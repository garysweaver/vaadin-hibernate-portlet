package com.vaadin.demo.workoutlog;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import org.hibernate.Session;

import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.data.hbnutil.HbnContainer.EntityItem;
import com.vaadin.data.hbnutil.HbnContainer.SessionManager;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Window.Notification;

public class WorkoutLog extends Application implements SessionManager,
        ClickListener, ValueChangeListener {
    private Table table;
    private WorkoutEditor editor = new WorkoutEditor(this);

    private Object actionButtonColumnMode = false;

    private static final Object ACTION_MODE_NONE = "no button ncolumn";
    private static final Object ACTION_MODE_GENERATOR = "buttons with column generator";
    private static final Object ACTION_MODE_CONTAINER = "buttons with extended container";

    private NativeSelect actionButtonMode = new NativeSelect();

    private Button addWorkoutButton;
    private Button toggleEditModeButton;
    private Button insertSomeRows;
    private Button insertQuiteManyRows;
    private Button insertManyRows;
    private Button help;
    private Button memUsage;
    private HelpWindow helpwindow;
    private VerticalLayout mainLayout;

    /**
     * This column generator transforms trainingType column to human readable
     * form (instead of displaying the identifier of referenced entity)
     */
    private ColumnGenerator traininTypeReadOnlyColumn = new ColumnGenerator() {
        public Component generateCell(Table source, Object itemId,
                Object columnId) {
            Label l = new Label();
            Object id = table.getContainerProperty(itemId, "trainingType")
                    .getValue();
            if (id == null) {
                l.setValue(" - ");
            } else {
                Type trainingtype = (Type) getSession().get(Type.class,
                        (Serializable) id);
                l.setValue(trainingtype.getTitle());
            }
            return l;
        }
    };

    @Override
    public void init() {
        attachVaadinTransactionListener();
        buildView();
    }

    /**
     * We are using session-per-request pattern with Hibernate. By using
     * Vaadin's transaction listener we can easily ensure that session is closed
     * on each request without polluting our program code with extra logic.
     */
    private void attachVaadinTransactionListener() {
        getContext().addTransactionListener(new TransactionListener() {
            public void transactionEnd(Application application,
                    Object transactionData) {
                // Transaction listener gets fired for all (Http) sessions
                // of Vaadin applications, checking to be this one.
                if (application == WorkoutLog.this) {
                    closeSession();
                }
            }

            public void transactionStart(Application application,
                    Object transactionData) {

            }
        });
    }

    private void closeSession() {
        Session sess = HibernateUtil.getSessionFactory().getCurrentSession();
        if (sess.getTransaction().isActive()) {
            sess.getTransaction().commit();
        }
        if (sess.isOpen()) {
            sess.close();
        }
    }

    /**
     * Used to get current Hibernate session. Also ensures an open Hibernate
     * transaction.
     */
    public Session getSession() {
        Session currentSession = HibernateUtil.getSessionFactory()
                .getCurrentSession();
        if (!currentSession.getTransaction().isActive()) {
            currentSession.beginTransaction();
        }
        return currentSession;
    }

    /**
     * Builds a simple view for application with Table and a row of buttons
     * below it.
     */
    private void buildView() {

        final Window w = new Window("Workout Log");

        // set theme and some layout stuff
        setMainWindow(w);
        w.getLayout().setSizeFull();
        w.getLayout().setMargin(false);

        Panel p = new Panel("Workout Log");
        p.setStyleName(Panel.STYLE_LIGHT);
        w.addComponent(p);
        mainLayout = new VerticalLayout();
        p.setLayout(mainLayout);

        mainLayout.addComponent(new Label("This is a Vaadin example "
                + "application that is using Hibernate to persist its model"
                + " objects. Check help for more details."));

        HorizontalLayout ol = new HorizontalLayout();

        addWorkoutButton = new Button("Add workout", this);
        ol.addComponent(addWorkoutButton);

        toggleEditModeButton = new Button("Toggle edit mode in table", this);
        ol.addComponent(toggleEditModeButton);

        insertSomeRows = new Button("Insert 5 rows", this);
        ol.addComponent(insertSomeRows);

        insertQuiteManyRows = new Button("Insert 1 000 rows", this);
        ol.addComponent(insertQuiteManyRows);

        insertManyRows = new Button("Insert 10 000 rows", this);
        ol.addComponent(insertManyRows);

        actionButtonMode.addItem(ACTION_MODE_NONE);
        actionButtonMode.addItem(ACTION_MODE_GENERATOR);
        actionButtonMode.addItem(ACTION_MODE_CONTAINER);
        actionButtonMode.setValue(ACTION_MODE_NONE);
        actionButtonMode.setImmediate(true);
        ol.addComponent(actionButtonMode);
        actionButtonMode.addListener(this);

        help = new Button("Help", this);
        ol.addComponent(help);

        memUsage = new Button("Mem info", this);
        ol.addComponent(memUsage);

        mainLayout.addComponent(ol);

        populateAndConfigureTable();

        mainLayout.addComponent(table);

        // make table consume all extra space
        p.setSizeFull();
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(table, 1);
        table.setSizeFull();
    }

    protected void populateAndConfigureTable() {
        table = new Table();

        table.setWidth("100%");
        table.setSelectable(true);
        table.setImmediate(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnWidth("date", 200);
        table.setColumnWidth("kilometers", 100);
        table.addListener(this);
        table.setTableFieldFactory(new MyFieldFactory(this));
        // add context menus for rows
        table.addActionHandler(new Handler() {
            Action add = new Action("Add new row to table");
            Action remove = new Action("Delete this row");
            Action edit = new Action("Edit this row");
            Action[] actions = new Action[] { add, remove, edit };

            public Action[] getActions(Object target, Object sender) {
                return actions;
            }

            public void handleAction(Action action, Object sender, Object target) {
                if (action == add) {
                    newRow();
                } else if (action == remove) {
                    Workout w = (Workout) getSession().get(Workout.class,
                            (Serializable) target);
                    deleteRun(w);
                } else if (action == edit) {
                    editRun((Long) target);
                }
            }
        });

        loadWorkouts();
    }

    /**
     * Loads container to Table
     */
    protected void loadWorkouts() {
        final HbnContainer<Workout> cont;
        if (actionButtonColumnMode == ACTION_MODE_CONTAINER) {
            // Use customized HbnContainer with extra column
            cont = new WorkoutListingWithSteroids(this);
        } else {
            // Use plain HbnContainer
            cont = new HbnContainer<Workout>(Workout.class, this);
        }
        table.setContainerDataSource(cont);
        if (actionButtonColumnMode == ACTION_MODE_GENERATOR) {
            table.removeGeneratedColumn("actions");
            table.addGeneratedColumn("actions", new ColumnGenerator() {

                public Component generateCell(Table source,
                        final Object itemId, Object columnId) {
                    HorizontalLayout actions = new HorizontalLayout();
                    Button b = new Button("Edit");
                    b.addListener(new ClickListener() {
                        public void buttonClick(ClickEvent event) {
                            editRun((Workout) ((EntityItem) cont
                                    .getItem(itemId)).getPojo());
                        }
                    });
                    actions.addComponent(b);
                    Button del = new Button("Delete");
                    del.addListener(new ClickListener() {
                        public void buttonClick(ClickEvent event) {
                            deleteRun((Workout) ((EntityItem) cont
                                    .getItem(itemId)).getPojo());
                        }
                    });
                    actions.addComponent(del);

                    return actions;
                }
            });
        }

        table.removeGeneratedColumn("trainingType");
        table.addGeneratedColumn("trainingType", traininTypeReadOnlyColumn);
    }

    /**
     * Saves changes made to Workout object to Hibernate Session. Note that run
     * is most likely detached due session-per-request patterns so we'll use
     * merge. Actual database update will happen by Vaadin's transaction
     * listener in the end of request.
     * 
     * If one wanted to make sure that this operation will be successful a
     * (Hibernate) transaction commit and error checking ought to be done.
     * 
     * @param run
     */
    public void persistRun(Workout run) {
        getSession().merge(run);
        table.setValue(null);
    }

    /**
     * Delete given Workout object from Table. Table will delegate deletion to
     * its container.
     * 
     * @param run
     */
    public void deleteRun(Workout run) {
        Long id = run.getId();
        table.removeItem(id);
    }

    /**
     * Adds new row to Table and selects new row. Table will delegate Item
     * creation to its container.
     */
    private void newRow() {
        Object newItemId = table.addItem();
        // upen in editor window unless table is in content editable mode
        if (!table.isEditable()) {
            table.setValue(newItemId);
        }
    }

    /**
     * Loads given Workout object to WorkoutEditor.
     * 
     * @param pojo
     */
    public void editRun(Workout pojo) {
        editor.loadRun(pojo);
    }

    /**
     * Loads Workout with given id to WorkoutEditor.
     * 
     * @parma id
     */
    public void editRun(Long id) {
        if (id == null) {
            if (editor.getParent() != null) {
                ((Window) editor.getParent()).removeWindow(editor);
            }
        } else {
            Workout pojo = (Workout) getSession().get(Workout.class, id);
            editRun(pojo);
        }
    }

    public void buttonClick(ClickEvent event) {
        if (addWorkoutButton == event.getButton()) {
            newRow();
        } else if (toggleEditModeButton == event.getButton()) {
            table.setEditable(!table.isEditable());
            // editable table shows select on trainingType property, readonly
            // shows generated column
            if (table.isEditable()) {
                table.removeGeneratedColumn("trainingType");
            } else {
                table.addGeneratedColumn("trainingType",
                        traininTypeReadOnlyColumn);
            }
        } else if (insertManyRows == event.getButton()) {
            HibernateUtil.insertExampleData(10000);
            loadWorkouts();
        } else if (insertQuiteManyRows == event.getButton()) {
            HibernateUtil.insertExampleData(1000);
            loadWorkouts();
        } else if (insertSomeRows == event.getButton()) {
            HibernateUtil.insertExampleData(5);
            loadWorkouts();
        } else if (help == event.getButton()) {
            if (helpwindow == null) {
                helpwindow = new HelpWindow();
            }
            getMainWindow().addWindow(helpwindow);
        } else if (memUsage == event.getButton()) {
            System.gc();
            System.gc();
            System.gc();
            StringBuffer mem = new StringBuffer();
            MemoryMXBean mmBean = ManagementFactory.getMemoryMXBean();
            mem.append("Heap (kB):");
            mem.append(mmBean.getHeapMemoryUsage().getUsed() / 1024);
            mem.append(" | Non-Heap (kB):");
            mem.append(mmBean.getNonHeapMemoryUsage().getUsed() / 1024);

            getMainWindow().showNotification(mem.toString(), Notification.TYPE_WARNING_MESSAGE);
            
        }
    }

    /**
     * Value change listener for Table.
     */
    public void valueChange(ValueChangeEvent event) {
        if (event.getProperty() == table) {
            editRun((Long) table.getValue());
        } else if (event.getProperty() == actionButtonMode) {
            actionButtonColumnMode = event.getProperty().getValue();
            // re-create table to reset column widths
            Table old = table;
            populateAndConfigureTable();
            loadWorkouts();
            ((AbstractOrderedLayout) old.getParent()).replaceComponent(old,
                    table);
            mainLayout.setExpandRatio(table, 1);
            table.setSizeFull();

        }
    }

}
