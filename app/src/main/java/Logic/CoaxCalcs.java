package Logic;

import java.text.DecimalFormat;

public class CoaxCalcs {
    DecimalFormat df = new DecimalFormat("#.000");
    int column,row;
    double value;
    String [][] strArr={{"0", "0.2756", "0.391533333", "0.507466667", "0.6234", "0.694483333", "0.765566667", "0.83665", "0.907733333", "0.978816667", "1.0499", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0.2756", "0.391533333", "0.507466667", "0.6234", "0.694483333", "0.765566667", "0.83665", "0.907733333", "0.978816667", "1.0499", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0.084", "0.115", "0.1681", "0.2029", "0.2376", "0.2672", "0.9167", "0.3161", "0.3406", "0.365", "0.383666667", "0.402333333", "0.421", "0.439666667", "0.458333333", "0.477", "0.493", "0.509", "0.525", "0.5395", "0.554", "0.568", "0.582", "0.596", "0.61", "0.624", "0.634818182", "0.645636364", "0.656454545", "0.667272727", "0.678090909", "0.688909091", "0.699727273", "0.710545455", "0.721363636", "0.732181818", "0.743", "0.753818182", "0.764636364", "0.775454545", "0.786272727", "0.797090909", "0.807909091", "0.818727273", "0.829545455", "0.840363636", "0.851181818", "0.862", "0.872818182", "0.883636364", "0.894454545", "0.905272727", "0.916090909", "0.926909091", "0.937727273", "0.948545455", "0.959363636", "0.970181818", "0.981", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0.084", "0.115", "0.1681", "0.2029", "0.2376", "0.2672", "0.9167", "0.3161", "0.3406", "0.365", "0.383666667", "0.402333333", "0.421", "0.439666667", "0.458333333", "0.477", "0.493", "0.509", "0.525", "0.5395", "0.554", "0.568", "0.582", "0.596", "0.61", "0.624", "0.634818182", "0.645636364", "0.656454545", "0.667272727", "0.678090909", "0.688909091", "0.699727273", "0.710545455", "0.721363636", "0.732181818", "0.743", "0.753818182", "0.764636364", "0.775454545", "0.786272727", "0.797090909", "0.807909091", "0.818727273", "0.829545455", "0.840363636", "0.851181818", "0.862", "0.872818182", "0.883636364", "0.894454545", "0.905272727", "0.916090909", "0.926909091", "0.937727273", "0.948545455", "0.959363636", "0.970181818", "0.981", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0.075", "0.103", "0.151", "0.1818", "0.2126", "0.2389", "0.26067", "0.2824", "0.3042", "0.326", "0.342333333", "0.358666667", "0.375", "0.391333333", "0.407666667", "0.424", "0.438", "0.452", "0.466", "0.4795", "0.493", "0.5052", "0.5174", "0.5296", "0.5418", "0.554", "0.563424242", "0.572848485", "0.582272727", "0.59169697", "0.601121212", "0.610545455", "0.619969697", "0.629393939", "0.638818182", "0.648242424", "0.657666667", "0.667090909", "0.676515152", "0.685939394", "0.695363636", "0.704787879", "0.714212121", "0.723636364", "0.733060606", "0.742484848", "0.751909091", "0.761333333", "0.770757576", "0.780181818", "0.789606061", "0.799030303", "0.808454545", "0.817878788", "0.82730303", "0.836727273", "0.846151515", "0.855575758", "0.865", "0.872409091", "0.879818182", "0.887227273", "0.894636364", "0.902045455", "0.909454545", "0.916863636", "0.924272727", "0.931681818", "0.939090909", "0.9465", "0.953909091", "0.961318182", "0.968727273", "0.976136364", "0.983545455", "0.990954545", "0.998363636", "1.005772727", "1.013181818", "1.020590909", "1.028"},
            {"0.029", "0.0395", "0.05786", "0.0703", "0.0828", "0.0933", "0.102", "0.1107", "0.1193", "0.128", "0.134666667", "0.141333333", "0.148", "0.154666667", "0.161333333", "0.168", "0.174", "0.18", "0.186", "0.191", "0.196", "0.2012", "0.2064", "0.2116", "0.2168", "0.222", "0.226030303", "0.230060606", "0.234090909", "0.238121212", "0.242151515", "0.246181818", "0.250212121", "0.254242424", "0.258272727", "0.26230303", "0.266333333", "0.270363636", "0.274393939", "0.278424242", "0.282454545", "0.286484848", "0.290515152", "0.294545455", "0.298575758", "0.302606061", "0.306636364", "0.310666667", "0.31469697", "0.318727273", "0.322757576", "0.326787879", "0.330818182", "0.334848485", "0.338878788", "0.342909091", "0.346939394", "0.350969697", "0.355", "0.358272727", "0.361545455", "0.364818182", "0.368090909", "0.371363636", "0.374636364", "0.377909091", "0.381181818", "0.384454545", "0.387727273", "0.391", "0.394272727", "0.397545455", "0.400818182", "0.404090909", "0.407363636", "0.410636364", "0.413909091", "0.417181818", "0.420454545", "0.423727273", "0.427"},
            {"0.2461", "0.3609", "0.470266667", "0.579633333", "0.689", "0.891316667", "1.093633333", "1.29595", "1.498266667", "1.700583333", "1.9029", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0.1608", "0.215844444", "0.270888889", "0.325933333", "0.380977778", "0.436022222", "0.491066667", "0.546111111", "0.601155556", "0.6562", "0.6890075", "0.721815", "0.7546225", "0.78743", "0.8202375", "0.853045", "0.8858525", "0.91866", "0.9514675", "0.984275", "1.0170825", "1.04989", "1.0826975", "1.115505", "1.1483125", "1.18112", "1.2139275", "1.246735", "1.2795425", "1.31235", "1.3451575", "1.377965", "1.4107725", "1.44358", "1.4763875", "1.509195", "1.5420025", "1.57481", "1.6076175", "1.640425", "1.6732325", "1.70604", "1.7388475", "1.771655", "1.8044625", "1.83727", "1.8700775", "1.902885", "1.9356925", "1.9685", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0.069", "0.091", "0.120666667", "0.150333333", "0.18", "0.20025", "0.2205", "0.24075", "0.261", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0.63", "0.68", "0.73", "0.78", "0.826666667", "0.873333333", "0.92", "0.96", "1", "1.04", "1.073333333", "1.106666667", "1.14", "1.176666667", "1.213333333", "1.25", "1.28", "1.31", "1.34", "1.37", "1.4", "1.43", "1.46", "1.49", "1.52", "1.55", "1.58", "1.61", "1.636666667", "1.663333333", "1.69", "1.716666667", "1.743333333", "1.77", "1.793333333", "1.816666667", "1.84", "1.866666667", "1.893333333", "1.92", "1.943333333", "1.966666667", "1.99", "2.013333333", "2.036666667", "2.06", "2.083333333", "2.106666667", "2.13", "2.153333333", "2.176666667", "2.2", "2.223333333", "2.246666667", "2.27", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0.489", "0.5125", "0.536", "0.559", "0.582", "0.605", "0.624333333", "0.643666667", "0.663", "0.684", "0.705", "0.7225", "0.74", "0.757333333", "0.774666667", "0.792", "0.809333333", "0.826666667", "0.844", "0.8625", "0.881", "0.898", "0.915", "0.932", "0.949", "0.966", "0.9812", "0.9964", "1.0116", "1.0268", "1.042", "1.0582", "1.0744", "1.0906", "1.1068", "1.123", "1.1392", "1.1554", "1.1716", "1.1878", "1.204", "1.2188", "1.2336", "1.2484", "1.2632", "1.278", "1.293", "1.307", "1.321", "1.335", "1.349", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0.221155862", "0.234087586", "0.257161379", "0.279961034", "0.300681071", "0.319207931", "0.336454138", "0.352534286", "0.367465862", "0.381703929", "0.395341034", "0.411808214", "0.42501", "0.441275714", "0.460776897", "0.473617586", "0.488955714", "0.498007586", "0.509964643", "0.518137586", "0.526144286", "0.536677241", "0.552842759", "0.560780714", "0.57161", "0.578958571", "0.587888966", "0.593401786", "0.60425", "0.614824138", "0.627250714", "0.640704483", "0.653608214", "0.667762069", "0.679111786", "0.690444828", "0.708582069", "0.717514643", "0.730277931", "0.745082143", "0.755161724", "0.760958214", "0.76955", "0.785278621", "0.785203214", "0.798793793", "0.808947857", "0.815993448", "0.824946786", "0.833883448", "0.842433448", "0.844652143", "0.868257241", "0.862676071", "0.875682759", "0.897487586", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0.744993448", "0.832523448", "0.9107125", "0.985311379", "1.055003571", "1.1201", "1.183714286", "1.240551724", "1.300027586", "1.361003571", "1.41772069", "1.489671429", "1.532113793", "1.592432143", "1.656841379", "1.734658621", "1.803207143", "1.889982759", "1.992682143", "2.102641379", "2.222675", "2.332510345", "2.416224138", "2.456532143", "2.445824138", "2.404642857", "2.380689655", "2.362478571", "2.369755172", "2.375", "2.409192857", "2.42777931", "2.452232143", "2.46472069", "2.488796429", "2.527462069", "2.554265517", "2.610125", "2.606644828", "2.633607143", "2.6631", "2.719439286", "2.735786207", "2.738155172", "2.739392857", "2.792831034", "2.846553571", "2.885489655", "2.895814286", "2.946214286", "2.987013793", "3.037271429", "3.061237931", "3.076171429", "3.114765517", "3.164525", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"}};

    public CoaxCalcs()
    {
        column=0;
        row=0;
    }

    public double totalLoss(double length, double attenuation)
    {
        return Double.parseDouble(df.format(length*attenuation));
    }

    public double fetchAttenuation(int row_)
    {
        row=row_;
        return Double.parseDouble(df.format(Double.parseDouble(strArr[row][column])));
    }

    public double attenuationLoss_dB_m(double number)
    {
        if(number<50.0d) {
        return 0.0d;
        }
        if (number >= 50.0d && number < 100.0d) {
            column = 0;
        } else if (number >= 100.0d && number < 200.0d) {
            column = 1;
        } else if (number >= 200.0d && number < 300.0d) {
            column = 2;
        } else if (number >= 300.0d && number < 400.0d) {
            column = 3;
        } else if (number >= 400.0d && number < 500.0d) {
            column = 4;
        } else if (number >= 500.0d && number < 600.0d) {
            column = 5;
        } else if (number >= 600.0d && number < 700.0d) {
            column = 6;
        } else if (number >= 700.0d && number < 800.0d) {
            column = 7;
        } else if (number >= 800.0d && number < 900.0d) {
            column = 8;
        } else if (number >= 900.0d && number < 1000.0d) {
            column = 9;
        } else if (number >= 1000.0d && number < 1100.0d) {
            column = 10;
        } else if (number >= 1100.0d && number < 1200.0d) {
            column = 11;
        } else if (number >= 1200.0d && number < 1300.0d) {
            column = 12;
        } else if (number >= 1300.0d && number < 1400.0d) {
            column = 13;
        } else if (number >= 1400.0d && number < 1500.0d) {
            column = 14;
        } else if (number >= 1500.0d && number < 1600.0d) {
            column = 15;
        } else if (number >= 1600.0d && number < 1700.0d) {
            column = 16;
        } else if (number >= 1700.0d && number < 1800.0d) {
            column = 17;
        } else if (number >= 1800.0d && number < 1900.0d) {
            column = 18;
        } else if (number >= 1900.0d && number < 2000.0d) {
            column = 19;
        } else if (number >= 2000.0d && number < 2100.0d) {
            column = 20;
        } else if (number >= 2100.0d && number < 2200.0d) {
            column = 21;
        } else if (number >= 2200.0d && number < 2300.0d) {
            column = 22;
        } else if (number >= 2300.0d && number < 2400.0d) {
            column = 23;
        } else if (number >= 2400.0d && number < 2500.0d) {
            column = 24;
        } else if (number >= 2500.0d && number < 2600.0d) {
            column = 25;
        } else if (number >= 2600.0d && number < 2700.0d) {
            column = 26;
        } else if (number >= 2700.0d && number < 2800.0d) {
            column = 27;
        } else if (number >= 2800.0d && number < 2900.0d) {
            column = 28;
        } else if (number >= 2900.0d && number < 3000.0d) {
            column = 29;
        } else if (number >= 3000.0d && number < 3100.0d) {
            column = 30;
        } else if (number >= 3100.0d && number < 3200.0d) {
            column = 31;
        } else if (number >= 3200.0d && number < 3300.0d) {
            column = 32;
        } else if (number >= 3300.0d && number < 3400.0d) {
            column = 33;
        } else if (number >= 3400.0d && number < 3500.0d) {
            column = 34;
        } else if (number >= 3500.0d && number < 3600.0d) {
            column = 35;
        } else if (number >= 3600.0d && number < 3700.0d) {
            column = 36;
        } else if (number >= 3700.0d && number < 3800.0d) {
            column = 37;
        } else if (number >= 3800.0d && number < 3900.0d) {
            column = 38;
        } else if (number >= 3900.0d && number < 4000.0d) {
            column = 39;
        } else if (number >= 4000.0d && number < 4100.0d) {
            column = 40;
        } else if (number >= 4100.0d && number < 4200.0d) {
            column = 41;
        } else if (number >= 4200.0d && number < 4300.0d) {
            column = 42;
        } else if (number >= 4300.0d && number < 4400.0d) {
            column = 43;
        } else if (number >= 4400.0d && number < 4500.0d) {
            column = 44;
        } else if (number >= 4500.0d && number < 4600.0d) {
            column = 45;
        } else if (number >= 4600.0d && number < 4700.0d) {
            column = 46;
        } else if (number >= 4700.0d && number < 4800.0d) {
            column = 47;
        } else if (number >= 4800.0d && number < 4900.0d) {
            column = 48;
        } else if (number >= 4900.0d && number < 5000.0d) {
            column = 49;
        } else if (number >= 5000.0d && number < 5100.0d) {
            column = 50;
        } else if (number >= 5100.0d && number < 5200.0d) {
            column = 51;
        } else if (number >= 5200.0d && number < 5300.0d) {
            column = 52;
        } else if (number >= 5300.0d && number < 5400.0d) {
            column = 53;
        } else if (number >= 5400.0d && number < 5500.0d) {
            column = 54;
        } else if (number >= 5500.0d && number < 5600.0d) {
            column = 55;
        } else if (number >= 5600.0d && number < 5700.0d) {
            column = 56;
        } else if (number >= 5700.0d && number < 5800.0d) {
            column = 57;
        } else if (number >= 5800.0d && number < 5900.0d) {
            column = 58;
        } else if (number >= 5900.0d && number < 6000.0d) {
            column = 59;
        } else if (number >= 6000.0d && number < 6100.0d) {
            column = 60;
        } else if (number >= 6100.0d && number < 6200.0d) {
            column = 61;
        } else if (number >= 6200.0d && number < 6300.0d) {
            column = 62;
        } else if (number >= 6300.0d && number < 6400.0d) {
            column = 63;
        } else if (number >= 6400.0d && number < 6500.0d) {
            column = 64;
        } else if (number >= 6500.0d && number < 6600.0d) {
            column = 65;
        } else if (number >= 6600.0d && number < 6700.0d) {
            column = 66;
        } else if (number >= 6700.0d && number < 6800.0d) {
            column = 67;
        } else if (number >= 6800.0d && number < 6900.0d) {
            column = 68;
        } else if (number >= 6900.0d && number < 7000.0d) {
            column = 69;
        } else if (number >= 7000.0d && number < 7100.0d) {
            column = 70;
        } else if (number >= 7100.0d && number < 7200.0d) {
            column = 71;
        } else if (number >= 7200.0d && number < 7300.0d) {
            column = 72;
        } else if (number >= 7300.0d && number < 7400.0d) {
            column = 73;
        } else if (number >= 7400.0d && number < 7500.0d) {
            column = 74;
        } else if (number >= 7500.0d && number < 7600.0d) {
            column = 75;
        } else if (number >= 7600.0d && number < 7700.0d) {
            column = 76;
        } else if (number >= 7700.0d && number < 7800.0d) {
            column = 77;
        } else if (number >= 7800.0d && number < 7900.0d) {
            column = 78;
        } else if (number >= 7900.0d && number < 8000.0d) {
            column = 79;
        } else if (number >= 8000.0d && number < 8100.0d) {
            column = 80;
        }
        value = Double.parseDouble(df.format(Double.parseDouble(strArr[row][column])));

        return value;
    }

}
