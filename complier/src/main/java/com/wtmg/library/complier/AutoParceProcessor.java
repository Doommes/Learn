package com.wtmg.library.complier;

import com.google.common.collect.ImmutableList;
import com.wtmg.library.AutoParcel;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

@SupportedAnnotationTypes("com.wtmg.library.AutoParcel")
public class AutoParceProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        Collection<? extends Element> annotatedElements = env.getElementsAnnotatedWith(AutoParcel.class);

        List<TypeElement> types = new ImmutableList.Builder<TypeElement>()
                .addAll(ElementFilter.typesIn(annotatedElements))
                .build();
        return false;
    }
}
