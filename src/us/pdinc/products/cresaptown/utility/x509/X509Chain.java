package us.pdinc.products.cresaptown.utility.x509;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class X509Chain
{
    public static X509Certificate convert(String certificate) throws CertificateException
    {
        return null;
    }
    
    public static X509Certificate[] extract(Object x509s)
    {
        log.log(Level.WARNING, "unknown type: {0}", x509s.getClass());
        return null;
    }
}
