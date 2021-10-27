package org.eden.lovestation.exception.business;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException() {
        super("無法根據條件取得使用者角色(Role Not Found)");
    }
}
