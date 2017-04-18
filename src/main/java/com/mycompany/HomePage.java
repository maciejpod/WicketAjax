package com.mycompany;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;
    private List<String> temp = Arrays.asList("Ala", "Ma", "Kitkuna", "dsa", "dsadwq");
    private List<String> stringList = new ArrayList<>(temp);
    private String searchValue = "";

    public HomePage(final PageParameters parameters) {
        super(parameters);
        IModel<String> searchValueModel = new Model<>(searchValue);
        Form<Void> searchForm = new Form<>("searchForm");

        final IModel<List<String>> hateList = new LoadableDetachableModel() {
            protected List<String> load() {
                return stringList;
            }
        };
        final WebMarkupContainer listContainer = new WebMarkupContainer("theContainer");
        add(listContainer);
        listContainer.setOutputMarkupId(true);
        final ListView hateView = new ListView("hateList", hateList) {
            protected void populateItem(final ListItem item) {
                String mli = (String) item.getModelObject();
                item.add(new Label("hateName", mli));
            }
        };
        listContainer.add(hateView);
        final TextField<String> searchTextField = new TextField<>("searchFilter", searchValueModel);
        searchTextField.add(new AjaxFormComponentUpdatingBehavior("keyup") {
            @Override
            protected void onUpdate(AjaxRequestTarget art) {
                String value = (String) searchTextField.getDefaultModelObject();
                if (value != null) {
                    List<String> list = new ArrayList<>();
                    for (String string : stringList) {
                        if (string.contains(value)) {
                            list.add(string);
                        }
                    }
                    hateList.setObject(list);
                } else {
                    hateList.setObject(stringList);
                }
                art.add(listContainer);
            }
        });
        searchForm.add(searchTextField);
        add(searchForm);
    }
}
