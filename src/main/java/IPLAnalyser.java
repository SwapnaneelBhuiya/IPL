import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class IPLAnalyser {
    List<IPLMostRuns> IPLFileList;
    List<IPLWickets> IPLWicketList;
    public IPLAnalyser() {
        this.IPLFileList = new ArrayList<IPLMostRuns>();
        this.IPLWicketList=new ArrayList<IPLWickets>();
    }
    public int loadIPLdata(String csvFilePath) throws IPLAnalyserException {
        CsvToBean<IPLMostRuns> csvToBean;
        try (Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLMostRuns> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader,  IPLMostRuns.class);
            while (censusCSVIterator.hasNext()) {
                this.IPLFileList.add((censusCSVIterator.next()));
            }
            return IPLFileList.size();
        }
        catch(IllegalStateException e) {
            throw new IPLAnalyserException(e.getMessage(),
                    IPLAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }catch (IOException|RuntimeException e) {
            throw new IPLAnalyserException(e.getMessage(),
                    IPLAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public int loadIPLBowlingData(String csvFilePath) throws IPLAnalyserException {
        CsvToBean<IPLWickets> csvToBean;
        try (Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLWickets> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader,  IPLWickets.class);
            while (censusCSVIterator.hasNext()) {
                this.IPLWicketList.add((censusCSVIterator.next()));
            }
            return IPLWicketList.size();
        }
        catch(IllegalStateException e) {
            throw new IPLAnalyserException(e.getMessage(),
                    IPLAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }catch (IOException|RuntimeException e) {
            throw new IPLAnalyserException(e.getMessage(),
                    IPLAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public String topBattingAverage(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.avg);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }
    public String topStrikeRate(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.sr);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }
    public String maxFoursAndSixes(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.fours+census.sixes);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IPLMostRuns> censusComparator) {
        for (int i = 0; i < IPLFileList.size(); i++) {
            for (int j = 0; j < IPLFileList.size() - i - 1; j++) {
                IPLMostRuns census1 = IPLFileList.get(j);
                IPLMostRuns census2 = IPLFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    IPLFileList.set(j, census2);
                    IPLFileList.set(j + 1, census1);
                }
            }
        }
    }
    private void sortWicket(Comparator<IPLWickets> censusComparator) {
        for (int i = 0; i < IPLWicketList.size(); i++) {
            for (int j = 0; j < IPLWicketList.size() - i - 1; j++) {
                IPLWickets census1 = IPLWicketList.get(j);
                IPLWickets census2 = IPLWicketList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    IPLWicketList.set(j, census2);
                    IPLWicketList.set(j + 1, census1);
                }
            }
        }
    }

    public String getPlayersWithTopSRandBoundary(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(IPLMostRuns::getSixes)
                                                            .thenComparing(census->census.fours)
                                                            .thenComparing(census->census.sr);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopAverageAndSR(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(IPLMostRuns::getAvg)
                .thenComparing(census->census.sr);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopAverageAndRuns(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(IPLMostRuns::getAvg).thenComparing(census->census.runs);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopBowlingAverage(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (IPLWicketList == null || IPLWicketList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(census->census.avg);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLWicketList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopSR(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (IPLWicketList == null || IPLWicketList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(census->census.strikeRate);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLWicketList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopEconomy(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (IPLWicketList == null || IPLWicketList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(census->census.economy);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLWicketList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopSRWithWickets(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (IPLWicketList == null || IPLWicketList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(IPLWickets::getStrikeRate)
                .thenComparing(census->census.fiveWicket).thenComparing(census->census.fourWicket);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLWicketList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopSRWAndAverage(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (IPLWicketList == null || IPLWicketList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(IPLWickets::getAvg)
                .thenComparing(census->census.strikeRate);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLWicketList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithMaxWickets(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (IPLWicketList == null || IPLWicketList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(census->census.wickets);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLWicketList);
        return sortedStateCensusJson;
    }
    public String getBatsmanWithMaxAverage(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(IPLMostRuns::getAvg);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopRuns(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.runs);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopHundreds(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.hundred);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopHundredsOrFifties(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (IPLFileList == null || IPLFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(IPLMostRuns::getHundred).thenComparing(census->census.fifty);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.IPLFileList);
        return sortedStateCensusJson;
    }
}