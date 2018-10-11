package ontology;

import jade.content.Predicate;
import jade.core.AID;

import java.util.List;

public class Joined implements Predicate {

    private List<AID> who;

    public List<AID> getWho() {
        return who;
    }

    public void setWho(List<AID> who) {
        this.who = who;
    }
}
