package ontology;

import jade.content.AgentAction;

public class ProposedPrice implements AgentAction {

    private float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
