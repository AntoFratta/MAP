package data;

public class ContinuousItem extends Item{

    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    double distance(Object obj) {
        ContinuousAttribute contAtt = (ContinuousAttribute) this.getAttribute();
        return Math.abs(contAtt.getScaledValue((double) this.getValue()) - contAtt.getScaledValue((double) obj));
    }

}
