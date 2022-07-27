package uz.oliymahad.oliymahadquroncourse.entity.enums;

import uz.oliymahad.oliymahadquroncourse.exception.DataNotFoundException;

import javax.xml.crypto.Data;

public enum Role {

    ROLE_USER(1),

    ROLE_ADMIN( 2),

    ROLE_OWNER(4);

    public final int flag;

    Role(int flag) {
        this.flag = flag;
    }

    public static  Role getRole(String roleName){
        for (Role role : Role.values()) {
            if(role.name().equals(roleName)){
                return role;
            }
        }
        throw DataNotFoundException.of("Role", roleName);
    }
}
