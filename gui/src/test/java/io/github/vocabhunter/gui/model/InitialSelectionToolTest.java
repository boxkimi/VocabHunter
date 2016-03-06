/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.model;

import io.github.vocabhunter.analysis.core.VocabHunterException;
import io.github.vocabhunter.analysis.session.WordState;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.vocabhunter.analysis.session.WordState.*;

public class InitialSelectionToolTest {
    @Test(expected = VocabHunterException.class)
    public void testNull() {
        InitialSelectionTool.nextWord(null);
    }

    @Test(expected = VocabHunterException.class)
    public void testEmpty() {
        InitialSelectionTool.nextWord(words());
    }

    @Test
    public void testSingleUnseen() {
        validate(0, UNSEEN);
    }

    @Test
    public void testSingleKnown() {
        validate(0, KNOWN);
    }

    @Test
    public void testInitial() {
        validate(0, UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN, UNSEEN);
    }

    @Test
    public void testUnfinishedList() {
        validate(5, UNSEEN, KNOWN, UNKNOWN, UNSEEN, KNOWN, UNSEEN, UNSEEN, UNSEEN);
    }

    @Test
    public void testFinishedList() {
        validate(7, KNOWN, KNOWN, UNKNOWN, KNOWN, KNOWN, UNKNOWN, KNOWN, KNOWN);
    }

    @Test
    public void testFinishedListWithGaps() {
        validate(7, KNOWN, KNOWN, UNKNOWN, UNSEEN, KNOWN, UNKNOWN, KNOWN, KNOWN);
    }

    private void validate(final int expectedIndex, final WordState... states) {
        List<WordModel> words = words(states);
        WordModel result = InitialSelectionTool.nextWord(words);

        Assert.assertEquals("Selected word index", expectedIndex, result.getSequenceNo());
    }

    private List<WordModel> words(final WordState... states) {
        List<WordModel> result = new ArrayList<>(states.length);
        int index = 0;

        for (WordState state : states) {
            WordModel model = new WordModel(index, "word " + index, Collections.emptyList(), 0, state);
            result.add(model);
            ++index;
        }

        return result;
    }
}