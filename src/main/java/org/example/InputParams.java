package org.example;

public class InputParams {

    private String batchId;

    private String source;

    private String block;

    private String blockDesc;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBlockDesc() {
        return blockDesc;
    }

    public void setBlockDesc(String blockDesc) {
        this.blockDesc = blockDesc;
    }

    public String getReportKey() {

        return this.source+"_"+this.block+"_"+this.blockDesc;
    }
}
