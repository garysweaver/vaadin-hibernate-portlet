package com.vaadin.demo.workoutlog;

import java.io.Serializable;
import java.util.Collection;

import com.vaadin.data.Property;
import com.vaadin.data.hbnutil.HbnContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This is a customized HbnContainer that adds an extra column to table (not in
 * DB). Extra column will contain two buttons that are hooked to run actions on
 * that row.
 */
@SuppressWarnings("unchecked")
public class WorkoutListingWithSteroids extends HbnContainer {
	private static final long serialVersionUID = -863642989917138011L;
	private WorkoutLog workoutlog;

	public WorkoutListingWithSteroids(WorkoutLog sessionMgr) {
		super(Workout.class, sessionMgr);
		workoutlog = sessionMgr;
	}

	@Override
	public Class getType(Object propertyId) {
		if ("actions".equals(propertyId)) {
			return HorizontalLayout.class;
		} else {
			return super.getType(propertyId);

		}
	}

	@Override
	public Collection getContainerPropertyIds() {
		Collection containerPropertyIds = super.getContainerPropertyIds();
		containerPropertyIds.add("actions");
		return containerPropertyIds;
	}

	@Override
	protected EntityItem loadItem(Serializable itemId) {
		// Use customized EntityItem from this class
		return new EntityItem(itemId);
	}

	public class EntityItem extends HbnContainer.EntityItem {

		public EntityItem(Serializable id) {
			super(id);
		}

		@Override
		public Property getItemProperty(Object id) {
			if ("actions".equals(id)) {
				return new ExtraActions();
			}
			Property p = (Property) properties.get(id);
			if (p == null) {
				p = new EntityItemProperty(id.toString());
				properties.put(id, p);
			}
			return p;
		}

		@Override
		public Collection getItemPropertyIds() {
			Collection itemPropertyIds = super.getItemPropertyIds();
			itemPropertyIds.add("actions");
			return itemPropertyIds;
		}

		/**
		 * 
		 */
		public class ExtraActions extends HorizontalLayout implements Property {
			public ExtraActions() {
				super();
				Button b = new Button("Edit");
				b.addListener(new ClickListener() {
					public void buttonClick(ClickEvent event) {
						workoutlog.editRun((Workout) getPojo());
					}
				});
				addComponent(b);
				Button del = new Button("Delete");
				del.addListener(new ClickListener() {
					public void buttonClick(ClickEvent event) {
						workoutlog.deleteRun((Workout) getPojo());
					}
				});
				addComponent(del);
			}

			public Class getType() {
				return HorizontalLayout.class;
			}

			public Object getValue() {
				return this;
			}

			public void setValue(Object newValue) throws ReadOnlyException,
					ConversionException {
				throw new ReadOnlyException();

			}

			@Override
			public boolean isReadOnly() {
				return true;
			}

		}
	}
}
