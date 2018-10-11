package ontology;

import jade.content.onto.BasicOntology;
import jade.content.onto.CFReflectiveIntrospector;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;

public class SmartParkingsOntology extends Ontology implements SmartParkingsVocabulary {

    // The singleton instance of this ontology
    private static Ontology theInstance = new SmartParkingsOntology();

    private SmartParkingsOntology() {
        super(ONTOLOGY_NAME, BasicOntology.getInstance(), new CFReflectiveIntrospector());

        try {
            add(new PredicateSchema(JOINED), Joined.class);
            add(new PredicateSchema(LEFT), Left.class);
            add(new PredicateSchema(SPOKEN), Spoken.class);
            PredicateSchema ps = (PredicateSchema) getSchema(JOINED);
            ps.add(JOINED_WHO, (ConceptSchema) getSchema(BasicOntology.AID), 1, ObjectSchema.UNLIMITED);

            ps = (PredicateSchema) getSchema(LEFT);
            ps.add(LEFT_WHO, (ConceptSchema) getSchema(BasicOntology.AID), 1, ObjectSchema.UNLIMITED);

            ps = (PredicateSchema) getSchema(SPOKEN);
            ps.add(SPOKEN_WHAT, getSchema(BasicOntology.STRING));
        } catch (OntologyException oe) {
            oe.printStackTrace();
        }

    }

    public static Ontology getInstance() {
        return theInstance;
    }
}
