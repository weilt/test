package com.demo.test.Enum;

public enum ActionEnum {
    /**
     * UP 点赞
     * DELETE 取消点赞
     */

    UP(1),DELETE(0);

    private int action;

    ActionEnum(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
