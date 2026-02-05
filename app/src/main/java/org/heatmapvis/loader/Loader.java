package org.heatmapvis.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.heatmapvis.util.DataLogTypeParsingUnit;
import org.heatmapvis.util.FileSelector;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.util.datalog.DataLogReader;
import edu.wpi.first.util.datalog.DataLogRecord.StartRecordData;
import edu.wpi.first.util.struct.BadSchemaException;
import edu.wpi.first.util.struct.StructDescriptor;
import edu.wpi.first.util.struct.StructDescriptorDatabase;

public class Loader {
    public static Map<String, ArrayList<ArrayList<Translation3d>>> Load() {
        DataLogReader reader = null;

        try {
            reader = new DataLogReader(new FileSelector().getChoosenPath());
        } catch (IOException e) {
            return new HashMap<>();
        }

        // Read the log and get all data
        Map<Integer, StartRecordData> entryIDToDataLogMap = new HashMap<>();
        List<DataLogTypeParsingUnit> DLTPUs = new ArrayList<>();

        reader.forEach(record -> {
            if (record.isStart()) {
                entryIDToDataLogMap.put(record.getStartData().entry, record.getStartData());
            } else if (!record.isControl()) {
                DLTPUs.add(new DataLogTypeParsingUnit(record, entryIDToDataLogMap.get(record.getEntry())));
            }
        });

        // Filter the data into only Translation3d[]
        Map<String, ArrayList<DataLogTypeParsingUnit>> filtered = new HashMap<>();
        DLTPUs.forEach(DLTPU -> {
            if (!DLTPU.type.equals("struct:Translation3d[]")) {
                return;
            }

            if (!filtered.containsKey(DLTPU.name)) {
                filtered.put(DLTPU.name, new ArrayList<>());
            }

            filtered.computeIfAbsent(DLTPU.name, k -> new ArrayList<>())
                .add(DLTPU);
        });

        // turn the Translation3d[] into actual usable Translation3d's
        Map<String, ArrayList<ArrayList<Translation3d>>> out = new HashMap<>();

        StructDescriptorDatabase sdd = new StructDescriptorDatabase();
        try {
            sdd.add(Translation3d.struct.getTypeName(), Translation3d.struct.getSchema());
        } catch (BadSchemaException e) {}
        StructDescriptor t3dd = sdd.find(Translation3d.struct.getTypeName());

        filtered.forEach((k, v) -> {
            out.computeIfAbsent(k, array -> new ArrayList<>());
            v.forEach(DLTPU -> {
                int numElements = DLTPU.data.remaining() / t3dd.getSize();
                ArrayList<Translation3d> data = new ArrayList<>();

                for (int i = 0; i < numElements; i++) {
                    data.add(Translation3d.struct.unpack(DLTPU.data.alignedSlice(t3dd.getSize())));
                }

                out.get(k).add(data);
            });
        });

        return out;
    }
}
