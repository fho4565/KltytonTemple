package io.github.kltyton.temple.idea;

import com.demonwav.mcdev.creator.custom.CreatorContext;
import com.demonwav.mcdev.creator.custom.TemplatePropertyDescriptor;
import com.demonwav.mcdev.creator.custom.types.CreatorProperty;
import com.demonwav.mcdev.creator.custom.types.CreatorPropertyFactory;
import com.intellij.openapi.observable.properties.GraphProperty;
import com.intellij.ui.dsl.builder.Panel;
import io.github.kltyton.temple.workspace.BuildScripts.Dsl;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import kotlin.Unit;

public final class BuildDslProperty extends CreatorProperty<String> {
    private final GraphProperty<String> value;
    public BuildDslProperty(TemplatePropertyDescriptor descriptor, CreatorContext context) {
        super(descriptor, context, String.class);
        value = context.getGraph().property("groovy");
    }
    @Override public GraphProperty<String> getGraphProperty() { return value; }
    @Override public String createDefaultValue(Object raw) { return Dsl.parse(raw == null ? "groovy" : raw.toString()).id; }
    @Override public String serialize(String input) { return Dsl.parse(input).id; }
    @Override public String deserialize(String input) { return Dsl.parse(input).id; }
    @Override public void buildUi(Panel panel) {
        buildUi(panel, new JLabel("Gradle 脚本语言 / Gradle script language"));
    }
    public void buildUi(Panel panel, JLabel label) {
        JComboBox<Dsl> choices = new JComboBox<>(Dsl.values());
        choices.setSelectedItem(Dsl.parse(value.get()));
        choices.addActionListener(event -> value.set(((Dsl) choices.getSelectedItem()).id));
        panel.row(label, row -> { row.cell(choices).resizableColumn(); return Unit.INSTANCE; });
    }
    public static final class Factory implements CreatorPropertyFactory {
        @Override public CreatorProperty<?> create(TemplatePropertyDescriptor descriptor, CreatorContext context) {
            return new BuildDslProperty(descriptor, context);
        }
    }
}
