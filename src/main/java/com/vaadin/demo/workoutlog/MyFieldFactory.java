/**
 * 
 */
package com.vaadin.demo.workoutlog;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.data.Validator;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.AbstractSelect.NewItemHandler;

public class MyFieldFactory extends DefaultFieldFactory {

    private HbnContainer<Type> trainingTypes;

    public MyFieldFactory(WorkoutLog app) {
        trainingTypes = new HbnContainer<Type>(Type.class, app);
    }

    @Override
    public Field createField(Container container, Object itemId,
            Object propertyId, Component uiContext) {

        /*
         * trainingType is manyToOne relation, give it a combobox
         */
        if (propertyId.equals("trainingType")) {
            return getTrainingTypeComboboxFor(itemId);
        }

        /*
         * Secondoarytypes is manyToMany relation, give it a multiselect list
         */
        if (propertyId.equals("secondaryTypes")) {
            return getSecondaryTypesList(itemId);
        }

        final Field f = super.createField(container, itemId, propertyId,
                uiContext);
        if (f != null) {
            if (f instanceof TextField) {
                TextField tf = (TextField) f;
                tf.setWidth("100%");
            }
            if (propertyId.equals("kilometers")) {
                f.setWidth("4em");
                f.addValidator(new Validator() {
                    public boolean isValid(Object value) {
                        try {
                            @SuppressWarnings("unused")
                            float f = Float.parseFloat((String) value);
                            return true;
                        } catch (Exception e) {
                            f.getWindow().showNotification("Bad number value");
                            f.setValue(0);
                            return false;
                        }
                    }

                    public void validate(Object value)
                            throws InvalidValueException {
                        // TODO Auto-generated method stub

                    }
                });
            }
            if (propertyId.equals("date")) {
                ((DateField) f).setResolution(DateField.RESOLUTION_MIN);
            }
        }
        return f;

    }

    private Map<Object, ListSelect> workoutIdToList = new HashMap<Object, ListSelect>();

    private Field getSecondaryTypesList(Object itemId) {
        ListSelect list = workoutIdToList.get(itemId);
        if (list == null) {
            list = new ListSelect();
            list.setMultiSelect(true);
            list.setContainerDataSource(trainingTypes);
            list.setItemCaptionPropertyId("title");
            list.setRows(4);
            workoutIdToList.put(itemId, list);
        }
        return list;
    }

    private Map<Object, ComboBox> workoutIdToCombobox = new HashMap<Object, ComboBox>();

    private Field getTrainingTypeComboboxFor(Object itemId) {
        ComboBox cb = workoutIdToCombobox.get(itemId);
        if (cb == null) {
            final ComboBox cb2 = new ComboBox();
            cb2.setContainerDataSource(trainingTypes);
            cb2.setItemCaptionPropertyId("title");
            cb2.setNewItemsAllowed(true);

            /*
             * NewItemHandler so that new training types can be added fluently
             * by typing it into combobox.
             */
            cb2.setNewItemHandler(new NewItemHandler() {
                public void addNewItem(String newItemCaption) {
                    Object identifier = trainingTypes.addItem();
                    trainingTypes.getContainerProperty(identifier, "title")
                            .setValue(newItemCaption);
                    cb2.setValue(identifier);
                }
            });
            workoutIdToCombobox.put(itemId, cb2);
            cb = cb2;
        }
        return cb;
    }

}