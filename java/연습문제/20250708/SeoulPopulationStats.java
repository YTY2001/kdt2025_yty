package ex1;                                   
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SeoulPopulationStats {

    static class District {
        private final String name;
        private final int male;
        private final int female;
        private final int households;

        District(String name, int male, int female, int households) {
            this.name = name;
            this.male = male;
            this.female = female;
            this.households = households;
        }

        String getName()                 { return name; }
        int    getMale()                 { return male; }
        int    getFemale()               { return female; }
        int    getHouseholds()           { return households; }
        int    getTotalPopulation()      { return male + female; }
        double getPopPerHousehold()      { return households > 0
                                               ? (double)(male + female) / households
                                               : 0; }
        @Override
        public String toString() {
            return String.format("%s ▶ 총인구:%d, 세대수:%d", 
                                  name, getTotalPopulation(), households);
        }
    }

    public static void main(String[] args) throws Exception {
        Path csv = Paths.get("seoul_people.csv");
        List<District> dists;
        try (Stream<String> lines = Files.lines(csv, Charset.forName("MS949"))) {
            dists = lines
                    .skip(1)                                  // 헤더 건너뛰기
                    .map(l -> l.split(",", -1))
                    .filter(f -> f.length >= 8)
                    .map(f -> new District(
                        f[1],                                 // 구 이름
                        Integer.parseInt(f[6]),               // 남자인구
                        Integer.parseInt(f[7]),               // 여자인구
                        Integer.parseInt(f[4])                // 세대수
                    ))
                    .collect(Collectors.toList());
        }

        // 1) 남자·여자 최대·최소
        District maxMale   = dists.stream()
                                  .max(Comparator.comparingInt(District::getMale))
                                  .get();
        District minMale   = dists.stream()
                                  .min(Comparator.comparingInt(District::getMale))
                                  .get();
        District maxFemale = dists.stream()
                                  .max(Comparator.comparingInt(District::getFemale))
                                  .get();
        District minFemale = dists.stream()
                                  .min(Comparator.comparingInt(District::getFemale))
                                  .get();

        // 2) 세대당 인구 최대·최소
        District maxPPH    = dists.stream()
                                  .max(Comparator.comparingDouble(District::getPopPerHousehold))
                                  .get();
        District minPPH    = dists.stream()
                                  .min(Comparator.comparingDouble(District::getPopPerHousehold))
                                  .get();

        // 3) 평균 구의 인구수
        double avgPop = dists.stream()
                             .mapToInt(District::getTotalPopulation)
                             .average()
                             .orElse(0);

        // 4) 결과 출력
        System.out.println("▶ 남자 인구 최대 구: "   + maxMale.getName()   + " (" + maxMale.getMale()   + "명)");
        System.out.println("▶ 남자 인구 최소 구: "   + minMale.getName()   + " (" + minMale.getMale()   + "명)");
        System.out.println("▶ 여자 인구 최대 구: "   + maxFemale.getName() + " (" + maxFemale.getFemale() + "명)");
        System.out.println("▶ 여자 인구 최소 구: "   + minFemale.getName() + " (" + minFemale.getFemale() + "명)");
        System.out.printf("▶ 세대당 인구 최대 구: %s (%.2f)%n",
                          maxPPH.getName(), maxPPH.getPopPerHousehold());
        System.out.printf("▶ 세대당 인구 최소 구: %s (%.2f)%n",
                          minPPH.getName(), minPPH.getPopPerHousehold());
        System.out.printf("▶ 평균 구의 인구수: %.1f명%n", avgPop);
    }
}
