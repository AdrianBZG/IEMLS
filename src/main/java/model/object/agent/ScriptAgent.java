package model.object.agent;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import model.object.MapObject;
import model.object.TypeObject;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.jruby.embed.EvalFailedException;
import org.jruby.embed.ScriptingContainer;
import org.jruby.exceptions.RaiseException;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import view.ObjectView;
import view.ScriptAgentView;

import java.util.Optional;

/**
 * Created by eleazardd on 2/01/17.
 */
public class ScriptAgent extends MapObject {

    public ScriptAgent() {}

    /**
     * Basic representation of what is this object
     *
     * @return enum
     */
    @Override
    public TypeObject getType() {
        return TypeObject.Resource;
    }

    /**
     * Representation of object in 2D dimension
     *
     * @return
     */
    @Override
    public ObjectView getVisualObject() {
        return new ScriptAgentView();
    }

    /**
     * Name of object to user display identification
     *
     * @return
     */
    @Override
    public String getName() {
        return "Scripted Agent";
    }

    /**
     * Is possible configure this object?
     *
     * @return boolean to question
     */
    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public void showOptions() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Agent Configuration");
        dialog.setHeaderText("You can edit: \n - appearance, \n - internal algorithm, \n - objectives, \n - and export and import agents");

        FontIcon icon = new FontIcon(FontAwesome.COGS);
        icon.setIconSize(64);
        dialog.setGraphic(icon);

        ButtonType applyChanges = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyChanges, ButtonType.CANCEL);

        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        VirtualizedScrollPane virtualizedScrollPane = new VirtualizedScrollPane<>(codeArea);

        dialog.getDialogPane().setContent(virtualizedScrollPane);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyChanges) {
                String code = codeArea.getText();
                ScriptingContainer scriptingContainer = new ScriptingContainer();
                try {
                    scriptingContainer.runScriptlet(code);
                }
                catch (RaiseException err) {
                    System.out.println(err.getMessage());
                }
                catch (EvalFailedException err) {

                }
                return null;
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
    }

}
