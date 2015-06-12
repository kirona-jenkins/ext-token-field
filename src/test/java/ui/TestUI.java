/*
 * Copyright 2015 Florian Schmitt, Explicatis GmbH <florian.schmitt@explicatis.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ui;

import java.util.LinkedList;
import java.util.List;

import com.explicatis.ext_token_field.ExtTokenField;
import com.explicatis.ext_token_field.SimpleTokenizable;
import com.explicatis.ext_token_field.Tokenizable;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import data.MyCustomBean;

@SuppressWarnings("serial")
@SpringUI
@Theme(ValoTheme.THEME_NAME)
@Widgetset(value = "com.explicatis.ext_token_field.WidgetSet")
public class TestUI extends UI
{

	private VerticalLayout	mainLayout;

	@Override
	protected void init(VaadinRequest vaadinRequest)
	{
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		ComboBox b = new ComboBox();
		b.setInputPrompt("Type here");
		b.addContainerProperty("label", String.class, "");
		Object addItem = b.addItem();
		b.getItem(addItem).getItemProperty("label").setValue("test");
		b.setItemCaptionPropertyId("label");

		ExtTokenField f = new ExtTokenField();
		f.setInputField(b);
		mainLayout.addComponent(f);

		b.addValueChangeListener(new ValueChangeListener()
		{

			@Override
			public void valueChange(ValueChangeEvent event)
			{
				Object id = event.getProperty().getValue();
				if (id != null)
				{
					Item item = b.getItem(id);
					String string = (String) item.getItemProperty("label").getValue();

					SimpleTokenizable t = new SimpleTokenizable(1, string);
					@SuppressWarnings("unchecked")
					List<Tokenizable> value = (List<Tokenizable>) f.getValue();
					if (value == null)
					{
						value = new LinkedList<>();
					}
					value.add(t);
					f.setValue(value);
					;
				}
			}
		});

		ComboBox b2 = new ComboBox();
		b2.setWidth(100, Unit.PERCENTAGE);
		b2.setInputPrompt("Type here");

		ExtTokenField f2 = new ExtTokenField();
		f2.setInputField(b2);
		mainLayout.addComponent(f2);

		List<SimpleTokenizable> list = new LinkedList<>();
		list.add(new SimpleTokenizable(123l, "ein Text"));
		list.add(new SimpleTokenizable(124l, "ein sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr langer Text"));

		f2.setValue(list);

		mainLayout.addComponent(getTestLayout3());

		setContent(mainLayout);
	}

	private HorizontalLayout getTestLayout3()
	{
		HorizontalLayout result = new HorizontalLayout();

		List<MyCustomBean> list = new LinkedList<>();
		list.add(new MyCustomBean(1, "Wert 1"));
		list.add(new MyCustomBean(2, "Wert 2"));
		list.add(new MyCustomBean(3, "Wert 3"));
		list.add(new MyCustomBean(4, "Wert 4"));

		ComboBox combo = new ComboBox();
		combo.setInputPrompt("Type here");

		ExtTokenField tokenField = new ExtTokenField();
		tokenField.setInputField(combo);
		result.addComponent(tokenField);

		tokenField.setValue(list);

		return result;
	}
}
