/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class NoNullsTest {

    @Test
    void getThrowsErrorIfNull() {
        new Assertion<>(
            "must throw error if contains null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).get(1),
            new Throws<>(
                "Item #1 of [1, null, 3] is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void setThrowsErrorIfArgumentNull() {
        new Assertion<>(
            "must throw error if set null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).set(2, null),
            new Throws<>(
                "Item can't be NULL in #set(2,T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void setThrowsErrorIfPreviousValueNull() {
        final ArrayList<Integer> list = new ArrayList<>(1);
        list.add(null);
        new Assertion<>(
            "must throw error if previous value is null",
            () -> new NoNulls<>(list).set(0, 2),
            new Throws<>(
                "Result of #set(0,T) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void addThrowsErrorIfArgumentNull() {
        new Assertion<>(
            "must throw error if add null",
            () -> {
                new NoNulls<>(new ArrayList<>(1)).add(0, null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #add(0,T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void removeThrowsErrorIfValueNull() {
        final ArrayList<Integer> list = new ArrayList<>(1);
        list.add(null);
        new Assertion<>(
            "must throw error if removed value is null",
            () -> new NoNulls<>(list).remove(0),
            new Throws<>(
                "Result of #remove(0) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void getThrowsErrorIfListIteratorNextValueIsNullValue() {
        new Assertion<>(
            "must throw error if removed value in iterator is null",
            () -> new NoNulls<>(
                new ListOf<>(null, 2, 3)
            ).listIterator().next(),
            new Throws<>(
                "Next item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void getThrowsErrorIfListIteratorPreviousValueIsNullValue() {
        final List<Integer> list = new ArrayList<>(2);
        list.add(1);
        list.add(2);
        final ListIterator<Integer> listiterator = new NoNulls<>(
            list
        ).listIterator();
        listiterator.next();
        list.set(0, null);
        new Assertion<>(
            "must throw error if previous value in iterator is null",
            listiterator::previous,
            new Throws<>(
                "Previous item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void testAddAll(){

        //creating list 1
        List<Integer> numList1=new ArrayList<>(Arrays.asList(1,2,3));
        NoNulls<Integer> noNullList1=new NoNulls<>(numList1);

        //creating list 2
        List<Integer> numList2=new ArrayList<>(Arrays.asList(4,5,6));
        NoNulls<Integer> noNullList2=new NoNulls<>(numList2);

        //creating result array to compare
        List<Integer> noNullsList3=new ArrayList<>(Arrays.asList(1,2,3,4,5,6));

        //testing addAll method
        noNullList1.addAll(noNullList2);

        Assertions.assertArrayEquals(noNullsList3.toArray(), noNullList1.toArray());


    }

    @Test()
    void testLastIndexOf(){

        // creating new list
        List<Integer> numList1=new ArrayList<>(Arrays.asList(1,2,3));
        NoNulls<Integer> noNullList1=new NoNulls<>(numList1);

        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                noNullList1.lastIndexOf(null);
            }
        },"Method fails when provided with null value");


    }
}
