package io.shmilyhe.convert.extra.functions;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import io.shmilyhe.convert.callee.IFunction;
import io.shmilyhe.convert.extra.tools.MD5;
import io.shmilyhe.convert.log.Log;
import io.shmilyhe.convert.log.api.Logger;
import io.shmilyhe.convert.tools.B64;
import io.shmilyhe.convert.tools.Bytes;
import io.shmilyhe.utils.encryption.HMAC;
import io.shmilyhe.utils.encryption.Hash;
import io.shmilyhe.utils.encryption.Sha1;
import io.shmilyhe.utils.encryption.Sha256;
import io.shmilyhe.utils.encryption.Sha512;

public class Encryption {
    static Logger log = Log.getLogger(Encryption.class);

    public static IFunction sha256(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            return new Sha256().sum(getByteArray(arg1));
        };
    }

    public static IFunction sha1(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            return new Sha1().sum(getByteArray(arg1));
        };
    }
    public static IFunction sha512(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            return new Sha512().sum(getByteArray(arg1));
        };
    }

    public static IFunction md5(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            return new MD5().sum(getByteArray(arg1));
        };
    }

    public static IFunction sign(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Object arg2= listGet(param,1);
            Object arg3= listGet(param,2);
            String hash=getString(arg1);
            byte[] data =getByteArray(arg2);
            String format=getString(arg3);
            Hash hs = getHash(hash);
            if(hs==null)return "unkown method!";
            byte[] rest =hs.sum(data);
            if("base64".equalsIgnoreCase(format)){
                return B64.encode(rest);
            }else{
                return Bytes.toHexString(rest);
            }
        };
    }



    public static IFunction hmac(){
        return (param,env)->{
            Object arg1= listGet(param,0);
            Object arg2= listGet(param,1);
            Object arg3= listGet(param,2);
            String hash=getString(arg1);
            byte[] key =getByteArray(arg2);
            byte[] data =getByteArray(arg3);
            Hash hs = getHash(hash);
            if(hs ==null){
                return new byte[0];
            }
            HMAC h = new HMAC(key,hs);
            return h.sum(data);
        };
    }

    private static Hash getHash(String name){
        if("sha256".equalsIgnoreCase(name)){
            return new Sha256();
        }else if("sha1".equalsIgnoreCase(name)){
            return new Sha1();
        }else if("sha512".equalsIgnoreCase(name)){
            return new Sha512();
        }if("md5".equalsIgnoreCase(name)){
            return new MD5();
        }
        log.warn("unkown hash {}",name);
        return null;
    }

    public static String getString(Object o){
        if(o==null)return null;
        if(o instanceof String){
            return (String)o;
        }
        return String.valueOf(o);
    }


    static Charset utf8=Charset.forName("utf-8");
    private static byte[] getByteArray(Object o){
        if(o==null)return new byte[0];
        if(o instanceof byte[])return (byte[])o;
        if(o instanceof Collection){
            try{
                Collection c=(Collection)o;
                byte[] ar= new byte[c.size()];
                int i=0;
                for(Object t:c){
                    ar[i++]=getByte(t);
                }
                return new Sha256().sum(ar);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(o instanceof String){
            return ((String)o).getBytes(utf8);
        }
        return new byte[0];
    }

    public static byte getByte(Object o){
        if(o instanceof Byte)return (byte)o;
        if(o instanceof Integer)return ((Integer)o).byteValue();
        if(o instanceof Long)return ((Long)o).byteValue();
        return 0;
    }

    private static Object listGet(List list,int index){
        if(list==null)return null;
        if(index<list.size()){
            return list.get(index);
        }else{
            return null;
        }
    }
}
