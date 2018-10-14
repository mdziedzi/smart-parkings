package ontology;

import jade.content.onto.BasicOntology;
import jade.content.onto.CFReflectiveIntrospector;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.PredicateSchema;

public class SmartParkingsOntology extends Ontology implements SmartParkingsVocabulary {

    // The singleton instance of this ontology
    private static Ontology theInstance = new SmartParkingsOntology();

    private SmartParkingsOntology() {
        super(ONTOLOGY_NAME, BasicOntology.getInstance(), new CFReflectiveIntrospector());
        try {
            add(new PredicateSchema(PROPOSED_PRICE), ProposedPrice.class);

            PredicateSchema ps = (PredicateSchema) getSchema(PROPOSED_PRICE);
            ps.add(PROPOSED_PRICE_VALUE, getSchema(BasicOntology.FLOAT));
        } catch (OntologyException oe) {
            oe.printStackTrace();
        }
    }

    public static Ontology getInstance() {
        return theInstance;
    }
}
