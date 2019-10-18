import com.sun.xml.internal.fastinfoset.algorithm.DoubleEncodingAlgorithm;

public class VRuntimeCalculator {
    public static final int[] PRIO_TO_WEIGHT = {
            /* -20 */ 88761,     71755,     56483,     46273,     36291,
            /* -15 */ 29154,     23254,     18705,     14949,     11916,
            /* -10 */ 9548,      7620,      6100,      4904,      3906,
            /* -5  */ 3121,      2501,      1991,      1586,      1277,
            /*  0  */ 1024,      820,       655,       526,       423,
            /*  5  */ 335,       272,       215,       172,       137,
            /*  10 */ 110,       87,        70,        56,        45,
            /*  15 */ 36,        29,        23,        18,        15,
    };

    private static final int NICE_0_VALUE = 1024;

    
    private Integer prioToAccess(Integer priority){
        return priority + 20;
    }


    void updateVRuntime(Process process, Integer currentCycle) {
        process.vruntime = process.vruntime +
                ((currentCycle * NICE_0_VALUE)/PRIO_TO_WEIGHT[prioToAccess(process.priority)]);
    }
}
