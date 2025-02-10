package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerCatalog;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerSupplier;

/**
 * Joker supplier implementation.
 * @author Nicolas Tazzieri
 */
public final class JokerSupplierImpl implements JokerSupplier {
    private static final int MISC_REP = 1;
    private static final int RARE_REP = 1;
    private static final int NOTCOMMON_REP = 2;
    private static final int COMMON_REP = 3;
    private static final int BASE_REP = 1;
    private final List<Joker> jokers;
    private final Random r = new Random();

    /**
     * Constructor.
     */
    public JokerSupplierImpl() {
        List<Joker> jList = getNewJokerList(List.of(), new JokerCatalogBase(), BASE_REP);
        jList = getNewJokerList(jList, new JokerCatalogCommon(), COMMON_REP);
        jList = getNewJokerList(jList, new JokerCatalogNotCommon(), NOTCOMMON_REP);
        jList = getNewJokerList(jList, new JokerCatalogRare(), RARE_REP);
        jList = getNewJokerList(jList, new JokerCatalogMisc(), MISC_REP);
        this.jokers = jList;
    }

    private List<Joker> getNewJokerList(final List<Joker> jokers, final JokerCatalog catalog, final int repetitions) {
        return mergeList(jokers, concatMultipleTimes(catalog.getJokerList(), repetitions));
    }

    private <X> List<X> mergeList(final List<X> l1, final List<X> l2) {
        return Stream.concat(l1.stream(), l2.stream()).toList();
    }

    private List<Joker> concatMultipleTimes(final List<Joker> toConcat, final int n) {
        return Stream.iterate(0, i -> i < n, i -> i + 1)
            .flatMap(i -> toConcat.stream())
            .toList();
    }

    @Override
    public Joker get() {
        return this.getRandom();
    }

    @Override
    public Joker getRandom() {
        return this.jokers.get(innerListIndex());
    }

    @Override
    public List<Joker> getJokerList() {
        return List.copyOf(this.jokers);
    }

    private int innerListIndex() {
        return r.nextInt(this.jokers.size());
    }
}
