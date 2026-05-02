package org.bowserfartgif.cugmod.content.propulsion.jet;

import org.bowserfartgif.cugmod.content.propulsion.jet.burner.BurnerBlock;
import org.bowserfartgif.cugmod.content.propulsion.jet.burner.BurnerBlockEntity;
import org.bowserfartgif.cugmod.content.propulsion.jet.compressor.CompressorBlock;
import org.bowserfartgif.cugmod.content.propulsion.jet.compressor.CompressorBlockEntity;
import org.bowserfartgif.cugmod.content.propulsion.jet.intake.IntakeBlock;
import org.bowserfartgif.cugmod.content.propulsion.jet.intake.IntakeBlockEntity;
import org.bowserfartgif.cugmod.content.propulsion.jet.nozzle.ThrusterBlock;
import org.bowserfartgif.cugmod.content.propulsion.jet.nozzle.ThrusterBlockEntity;

import static java.util.Map.Entry;

public class JetEngine {
    Entry<IntakeBlock, IntakeBlockEntity> intake;
    Entry<CompressorBlock, CompressorBlockEntity> compressor;
    Entry<BurnerBlock, BurnerBlockEntity> burner;
    Entry<ThrusterBlock, ThrusterBlockEntity> nozzle;

    public double velocity = 0;

    public double kBuffer = 1; //kg
    private double fBurnt = 0;

    private static final double keroseneLHV = 43_000_000; // J/kg
    private static final double oxyRatio = 3.482d; // Trust me bro

    public double getMassFlow(double v) {
        return v * 1.225 / 20;
    }

    public double getQ(double airMassFlow) {
        final double OXYGEN_MASS_FRACTION = 0.232;
        double oxygenMassFlow = airMassFlow * OXYGEN_MASS_FRACTION;
        double maxFuelFromOxygen = oxygenMassFlow / oxyRatio;
        fBurnt = Math.min(kBuffer, maxFuelFromOxygen);
        return fBurnt * keroseneLHV;
    }

    public void tick() {
        double airflow = getMassFlow(velocity) * 10; // no compressor function yet
        double totalMassFlow = airflow + fBurnt;

        double q = getQ(airflow);

        double v_ex = Math.sqrt(velocity * velocity + (2 * 0.5 * q) / totalMassFlow);
        double F = totalMassFlow * v_ex - airflow * velocity;

        nozzle.getValue().thrust = F;
    }
}
