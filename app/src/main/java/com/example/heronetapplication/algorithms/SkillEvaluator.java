package com.example.heronetapplication.algorithms;

public class SkillEvaluator {

    private static final int MAX_SKILL_LEVEL = 10;
    private static int[] skillLevel;

    public SkillEvaluator(int[] skillLevel) {
        this.skillLevel = skillLevel;
    }

    public static int evaluateSkillLevel() {
        int totalSkillLevel = 0;
        for (int i = 0; i < skillLevel.length; i++) {
            totalSkillLevel += skillLevel[i];
        }
        return totalSkillLevel / skillLevel.length;
    }





}
