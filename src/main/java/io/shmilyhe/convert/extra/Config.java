package io.shmilyhe.convert.extra;

import io.shmilyhe.convert.callee.FunctionTable;
import io.shmilyhe.convert.extra.functions.Encryption;

public class Config {

    public static void registryEncryption(){
        FunctionTable ft = new FunctionTable();
        ft.registry("hmac", Encryption.hmac());
        ft.registry("sign", Encryption.sign());
        ft.registry("sha1", Encryption.sha1());
        ft.registry("sha256", Encryption.sha256());
        ft.registry("sha512", Encryption.sha512());
        ft.registry("md5", Encryption.md5());
    }
    
}
