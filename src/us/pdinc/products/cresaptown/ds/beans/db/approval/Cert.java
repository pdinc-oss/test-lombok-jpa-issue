package us.pdinc.products.cresaptown.ds.beans.db.approval;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import lombok.Data;
import lombok.Getter;
import lombok.extern.java.Log;
import us.pdinc.products.cresaptown.utility.x509.X509Chain;

@Entity
@Table(schema = "cresaptown", name = "certs")
@Data
@Log
public class Cert
{
    @Getter(onMethod = @__({ @Id, @GeneratedValue }))
    protected Long id;
    protected String certificate;

    @Transient
    public X509Certificate getX509() throws CertificateException
    {
        return X509Chain.convert(null);
    }

    public static Cert getByX509Certificate(EntityManager em, String pem)
    { 
        log.log(Level.FINE, "message {0}", pem);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cert> cq = cb.createQuery(Cert.class);
        Root<Cert> rootEntry = cq.from(Cert.class);
        CriteriaQuery<Cert> all = cq.select(rootEntry).where(cb.equal(rootEntry.get(Cert_.certificate), pem));
        TypedQuery<Cert> query = em.createQuery(all);
        List<Cert> list = query.getResultList();
        if (list.size() == 0)
            return null;
        else if (list.size() == 1)
            return list.get(0);
        else
            throw new NonUniqueResultException();
    }

}
