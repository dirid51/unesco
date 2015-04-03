package org.dirid51.unesco.freemarker;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.dirid51.unesco.HeritageCountry;

import freemarker.template.AdapterTemplateModel;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import freemarker.template.TemplateSequenceModel;
import freemarker.template.WrappingTemplateModel;

public class HeritageCountryAdapter extends WrappingTemplateModel implements TemplateSequenceModel, TemplateCollectionModel, AdapterTemplateModel, TemplateModelIterator {
	
	private final HeritageCountry heritageCountry;
	
	public HeritageCountryAdapter(HeritageCountry heritageCountry, ObjectWrapper ow) {
		super(ow);
		this.heritageCountry = heritageCountry;
    }

	@Override
	public Object getAdaptedObject(@SuppressWarnings("rawtypes") Class hint) {
		return heritageCountry;
	}

	@Override
	public TemplateModel get(int index) throws TemplateModelException {
		return wrap(heritageCountry
						.getHeritageSites()
						.stream()
						.map(hs -> hs.getName())
						.sorted()
						.collect(Collectors.toCollection(ArrayList::new))
						.get(index));
	}

	@Override
	public int size() throws TemplateModelException {
		return this.heritageCountry.getHeritageSites().size();
	}

	@Override
    public TemplateModelIterator iterator() throws TemplateModelException {
	    return this;
    }

	@Override
    public TemplateModel next() throws TemplateModelException {
	    return wrap(heritageCountry.getHeritageSites().iterator().next());
    }

	@Override
    public boolean hasNext() throws TemplateModelException {
	    return heritageCountry.getHeritageSites().iterator().hasNext();
    }

}
