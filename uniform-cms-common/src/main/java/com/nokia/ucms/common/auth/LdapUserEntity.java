package com.nokia.ucms.common.auth;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LdapUserEntity
{
    private String displayName;
    private String uid;
    private String uidNumber;
    private String mail;
}
