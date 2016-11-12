package week5.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@NamedQueries({
	@NamedQuery(name = "Pod.findItems", query = "SELECT p.pod_id, d.address, d.name, d.phone from Pod p inner join p.pkg d "),
        @NamedQuery(name = "Pod.findPods",query = "SELECT p from Pod p")
})
@Entity
@Table(name="pod")
public class Pod implements Serializable {

    private static final long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer pod_id;

    private String note;

    @Lob
    @Column(length=100000)
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date delivery_date;

    private String ack_id;

    @OneToOne
    @JoinColumn(name="pkg_id", referencedColumnName="pkg_id")
    private Delivery pkg;

    public Delivery getPkg() {
        return pkg;
    }

    public void setPkg(Delivery pkg) {
        this.pkg = pkg;
    }

    public Integer getPod_id() {
        return pod_id;
    }

    public void setPod_id(Integer pod_id) {
        this.pod_id = pod_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }   

    public Date getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getAck_id() {
        return ack_id;
    }

    public void setAck_id(String ack_id) {
        this.ack_id = ack_id;
    }

    @Override
    public String toString() {
        return "Pod{" + "pod_id=" + pod_id + ", note=" + note + ", image=" + image + ", delivery_date=" + delivery_date + ", ack_id=" + ack_id + ", pkg=" + pkg + '}';
    }

    
}
