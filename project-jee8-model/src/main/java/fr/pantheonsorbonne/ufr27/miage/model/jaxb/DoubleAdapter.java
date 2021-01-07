package fr.pantheonsorbonne.ufr27.miage.model.jaxb;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleAdapter extends XmlAdapter <String, Double>
{


    public Double unmarshal(String value) {
        return ((double)javax.xml.bind.DatatypeConverter.parseDouble(value));
    }

    public String marshal(Double value) {
        if (value == null) {
            return null;
        }
        return (javax.xml.bind.DatatypeConverter.printDouble((double)(double)value));
    }

}
