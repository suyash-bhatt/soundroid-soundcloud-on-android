package org.soundroid.xml.converters;

import org.soundroid.xml.models.Tracks;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

public class TracksConverter extends CollectionConverter {

	public TracksConverter(Mapper mapper) {
		super(mapper);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return super.unmarshal(reader, context);
	}

	public boolean canConvert(Class type) {
		return Tracks.class.isAssignableFrom(type);
	}
}

