package model.object.agent;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import view.ObjectView.ObjectView;
import model.Expr.ExprLexer;
import model.Expr.ExprParser;
/**
 * TODO: Document it
 * Created by eleazardd on 10/01/17.
 */
public class SituationActionAgent extends Agent {



    /**
     * Representation of object in 2D dimension
     *
     * @return
     */
    @Override
    public ObjectView getVisualObject() {
        ANTLRInputStream in = new ANTLRInputStream("hello");

        ExprLexer exprLexer = new ExprLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(exprLexer);
        ExprParser parser = new ExprParser(tokens);

        return null;
    }

    /**
     * Name of object to user display identification
     *
     * @return
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Is possible configure this object?
     *
     * @return boolean to question
     */
    @Override
    public boolean hasOptions() {
        return false;
    }
}
