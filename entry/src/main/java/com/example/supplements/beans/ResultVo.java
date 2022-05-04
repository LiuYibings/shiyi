package com.example.supplements.beans;

public class ResultVo {
    private Boolean flag;
    private Object data;

    @Override
    public String toString() {
        return "ResultVo{" +
                "flag=" + flag +
                ", data=" + data +
                '}';
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultVo(Boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public ResultVo() {
    }
}
