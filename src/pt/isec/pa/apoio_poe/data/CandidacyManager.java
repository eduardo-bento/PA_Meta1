package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.model.Candidacy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CandidacyManager {
    private final Set<Candidacy> candidacies;

    public CandidacyManager() {
        candidacies = new HashSet<>();
    }
}