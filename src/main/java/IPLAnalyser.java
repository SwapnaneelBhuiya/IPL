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
    List<IPLMostRuns> csvFileList;
    List<IPLWickets> csvList;
    public IPLAnalyser() {
        this.csvFileList = new ArrayList<IPLMostRuns>();
        this.csvList=new ArrayList<IPLWickets>();
    }
    public int loadIPLdata(String csvFilePath) throws IPLAnalyserException {
        CsvToBean<IPLMostRuns> csvToBean;
        try (Reader reader= Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLMostRuns> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader,  IPLMostRuns.class);
            while (censusCSVIterator.hasNext()) {
                this.csvFileList.add((censusCSVIterator.next()));
            }
            return csvFileList.size();
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
                this.csvList.add((censusCSVIterator.next()));
            }
            return csvList.size();
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
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.avg);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }
    public String topStrikeRate(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.sr);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }
    public String maxFoursAndSixes(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(census->census.fours+census.sixes);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IPLMostRuns> censusComparator) {
        for (int i = 0; i < csvFileList.size(); i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                IPLMostRuns census1 = csvFileList.get(j);
                IPLMostRuns census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }
            }
        }
    }
    private void sortWicket(Comparator<IPLWickets> censusComparator) {
        for (int i = 0; i < csvList.size(); i++) {
            for (int j = 0; j < csvList.size() - i - 1; j++) {
                IPLWickets census1 = csvList.get(j);
                IPLWickets census2 = csvList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvList.set(j, census2);
                    csvList.set(j + 1, census1);
                }
            }
        }
    }

    public String getPlayersWithTopSRandBoundary(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(IPLMostRuns::getSixes)
                                                            .thenComparing(census->census.fours)
                                                            .thenComparing(census->census.sr);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopAverageAndSR(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(IPLMostRuns::getAvg)
                .thenComparing(census->census.sr);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopAverageAndRuns(String filePath) throws IPLAnalyserException {
        loadIPLdata(filePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLMostRuns> censusComparator= Comparator.comparing(IPLMostRuns::getAvg).thenComparing(census->census.runs);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopBowlingAverage(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (csvList == null || csvList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(census->census.avg);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopSR(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (csvList == null || csvList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(census->census.strikeRate);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvList);
        return sortedStateCensusJson;
    }

    public String getPlayerWithTopEconomy(String filePath) throws IPLAnalyserException {
        loadIPLBowlingData(filePath);
        if (csvList == null || csvList.size() == 0) {
            throw new IPLAnalyserException("NO_CENSUS_DATA", IPLAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IPLWickets> censusComparator= Comparator.comparing(census->census.economy);
        this.sortWicket(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvList);
        return sortedStateCensusJson;
    }
}