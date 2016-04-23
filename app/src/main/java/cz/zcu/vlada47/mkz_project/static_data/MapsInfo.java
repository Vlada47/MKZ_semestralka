package cz.zcu.vlada47.mkz_project.static_data;

/**
 * Created by Vlada47 on 26. 3. 2016.
 */
public enum MapsInfo {

    MAP01(0, "Map 0", 6, new int[] {8,11}, new int[][] {{6,16},{6,17},{7,16},{7,17},{8,16},{8,17}}),
    MAP02(1, "Map 1", 10, new int[] {4,7}, new int[][] {{1,1},{1,2},{2,1},{2,2},{3,1},{3,2},{4,1},{4,2},{5,1},{5,2}}),
    MAP03(2, "Map 2", 11, new int[] {1,14}, new int[][] {{6,1},{6,2},{6,3},{6,4},{7,2},{7,3},{7,4},{8,1},{8,2},{8,3},{8,4}}),
    MAP04(3, "Map 3", 20, new int[] {11,10}, new int[][] {{1,14},{1,15},{1,16},{1,17},{2,14},{2,15},{2,16},{2,17},{3,14},{3,15},{3,16},{3,17},{4,14},{4,15},{4,16},{4,17},{5,14},{5,15},{5,16},{5,17}}),
    MAP05(4, "Map 4", 12, new int[] {7,14}, new int[][] {{5,1},{5,2},{5,3},{5,4},{6,1},{6,2},{6,3},{6,4},{7,1},{7,2},{7,3},{7,4}});

    /**
     *
     */
    private int index;

    /**
     *
     */
    private String label;

    /**
     *
     */
    private int crateCnt;

    /**
     *
     */
    private int[] playerPos;

    /**
     *
     */
    private int[][] targetsPos;

    /**
     *
     * @param index
     * @param label
     * @param crateCnt
     * @param playerPos
     * @param targetsPos
     */
    MapsInfo(int index, String label, int crateCnt, int[] playerPos, int[][] targetsPos) {
        this.index = index;
        this.label = label;
        this.crateCnt = crateCnt;
        this.playerPos = playerPos;
        this.targetsPos = targetsPos;
    }

    public int getIndex() {
        return index;
    }

    public String getLabel() {
        return label;
    }

    public int getCrateCnt() {
        return crateCnt;
    }

    public int[] getPlayerPos() {
        return playerPos;
    }

    public int[][] getTargetsPos() {
        return targetsPos;
    }

    public String toString() {
        return label+", number of crates: "+crateCnt;
    }
}
