package com.vaadin.demo.workoutlog;

import java.util.Date;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class WorkoutEditor extends Window implements ClickListener {

    private DateField date = new DateField("Date");
    private TextField kilomiters = new TextField("Kilometers");
    private TextField title = new TextField("Title/note");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Workout run;
    private WorkoutLog workoutLog;

    public WorkoutEditor(WorkoutLog app) {
        super("Edig workout");
        workoutLog = app;
        Layout main = new VerticalLayout();
        setLayout(main);
        main.setSizeUndefined();
        main.setStyleName(Panel.STYLE_LIGHT);

        FormLayout form = new FormLayout();
        form.setSizeUndefined();
        date.setResolution(DateField.RESOLUTION_MIN);
        form.addComponent(date);
        form.addComponent(kilomiters);
        form.addComponent(title);
        main.addComponent(form);

        HorizontalLayout actions = new HorizontalLayout();
        actions.addComponent(save);

        save.addListener(this);

        actions.addComponent(delete);
        delete.addListener(this);

        main.addComponent(actions);

    }

    public void loadRun(Workout run) {
        if (run == null) {
            close();
        } else {
            date.setValue(run.getDate());
            kilomiters.setValue(run.getKilometers());
            title.setValue(run.getTitle());
            if (getParent() == null) {
                workoutLog.getMainWindow().addWindow(this);
            }
            kilomiters.focus();
            this.run = run;
        }
    }

    public void buttonClick(ClickEvent event) {
        if (event.getButton() == delete) {
            workoutLog.deleteRun(run);
        } else if (event.getButton() == save) {
            run.setDate((Date) date.getValue());
            run.setKilometers(Float
                    .parseFloat(kilomiters.getValue().toString()));
            run.setTitle((String) title.getValue());
            workoutLog.persistRun(run);
        }
        if (getParent() != null) {
            ((Window) getParent()).removeWindow(this);
        }
    }
}
