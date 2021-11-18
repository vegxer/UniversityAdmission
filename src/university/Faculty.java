package university;


import applicant.Applicant;

import java.util.HashMap;

public class Faculty {
    private String name = null;
    private HashMap<String, Integer> minSubjectsScores;

    public Faculty()
    {
        minSubjectsScores = new HashMap<>();
    }

    public boolean DoesApplicantPass(Applicant applicant) {
        for (String subject : minSubjectsScores.keySet())
        {
            if (!applicant.getSubjectScores().containsKey(subject))
                return false;

            if (applicant.getSubjectScores().get(subject) < minSubjectsScores.get(subject))
                return false;
        }

        return true;
    }


    public void addSubject(String subject, int minScore)
    {
        minSubjectsScores.put(subject, minScore);
    }

    public String getName() {
        if (name == null)
            throw new NullPointerException("Name are not initialized");

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getMinSubjectsScores() {
        return minSubjectsScores;
    }

    public void setMinSubjectsScores(HashMap<String, Integer> minSubjectScores) {
        this.minSubjectsScores = minSubjectScores;
    }
}
