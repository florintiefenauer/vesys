
package bank.soap.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "deposit", namespace = "http://soap.bank/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deposit", namespace = "http://soap.bank/")
public class Deposit {

    @XmlElement(name = "arg0", namespace = "")
    private double arg0;

    /**
     * 
     * @return
     *     returns double
     */
    public double getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(double arg0) {
        this.arg0 = arg0;
    }

}
