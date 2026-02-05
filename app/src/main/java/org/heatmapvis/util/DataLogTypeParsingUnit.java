package org.heatmapvis.util;

import java.nio.ByteBuffer;

import edu.wpi.first.util.datalog.DataLogRecord;

public class DataLogTypeParsingUnit {
    public DataLogTypeParsingUnit(int entry, long timestamp, ByteBuffer data, String name, String type,
            String metadata) {
        this.entry = entry;
        this.timestamp = timestamp;
        this.data = data;
        this.name = name;
        this.type = type;
        this.metadata = metadata;
    }

    public DataLogTypeParsingUnit(DataLogRecord DLR, DataLogRecord.StartRecordData SRD) {
        this.entry = DLR.getEntry();
        this.timestamp = DLR.getTimestamp();
        this.data = DLR.getRawBuffer();
        this.name = SRD.name;
        this.type = SRD.type;
        this.metadata = SRD.metadata;
    }

    public int entry;
    public long timestamp;
    public ByteBuffer data;
    public String name;
    public String type;
    public String metadata;

    public boolean getBoolean() {
        if ("boolean".equals(this.type)) {
            return this.data.get() != 0;
        } else {
            System.err.print("Type is not boolean!");
            return false;
        }
    }
}
