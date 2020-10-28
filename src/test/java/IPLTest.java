import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class IPLTest {
    private static final String IPL_MOST_RUNS = "./src/main/resources/IPL2019FactsheetMostRuns.csv";
    private static final String IPL_MOST_WICKETS="./src/main/resources/IPL2019FactsheetMostWkts.csv";
    @Test
    public void givenIPLMostRunsFindBestScore() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.topBattingAverage(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals(83.2, censusCSV[0].avg,0);
    }
    @Test
    public void givenIPLDataFindHighestStrikeRate() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.topStrikeRate(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals(333.33, censusCSV[0].sr,0);
    }
    @Test
    public void givenIPLDatafindMax6sAnd4s() throws IPLAnalyserException {
        IPLAnalyser iplAnalyser=new IPLAnalyser();
        String sorted=iplAnalyser.maxSixes(IPL_MOST_RUNS);
        IPLMostRuns[] censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals(52, censusCSV[0].sixes,0);
        sorted=iplAnalyser.maxFours(IPL_MOST_RUNS);
        censusCSV=new Gson().fromJson(sorted, IPLMostRuns[].class);
        Assert.assertEquals(64, censusCSV[0].fours,0);
    }
}