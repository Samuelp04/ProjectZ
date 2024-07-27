package com.swiftydevs.projectz.Common.init;

import java.util.Arrays;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModEntityGeneration {

    public static void onEntitySpawn(BiomeLoadingEvent event) {
        clearEntitySpawns(event, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER, EntityType.ENDERMAN, EntityType.SPIDER, EntityType.SLIME);
        addEntityToAllOverworldBiomes(event, ModEntityTypes.INFECTED_ZOMBIE.get(), 500, 2, 4);
    }

    @SafeVarargs
    private static void clearEntitySpawns(BiomeLoadingEvent event, EntityType<?>... entityTypes) {
        for (EntityType<?> entityType : entityTypes) {
            event.getSpawns().getSpawner(entityType.getCategory()).clear();
        }
    }

    @SafeVarargs
    private static void addEntityToAllBiomesExceptThese(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount, ResourceKey<Biome>... excludedBiomes) {
        if (!isBiomeExcluded(event, excludedBiomes)) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    @SafeVarargs
    private static void addEntityToSpecificBiomes(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount, ResourceKey<Biome>... includedBiomes) {
        if (isBiomeIncluded(event, includedBiomes)) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllOverworldBiomes(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount) {
        if (event.getCategory() != Biome.BiomeCategory.THEEND && event.getCategory() != Biome.BiomeCategory.NETHER) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllBiomesNoNether(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount) {
        if (event.getCategory() != Biome.BiomeCategory.NETHER) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllBiomesNoEnd(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount) {
        if (event.getCategory() != Biome.BiomeCategory.THEEND) {
            addEntityToAllBiomes(event, type, weight, minCount, maxCount);
        }
    }

    private static void addEntityToAllBiomes(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount) {
        List<MobSpawnSettings.SpawnerData> spawners = event.getSpawns().getSpawner(type.getCategory());
        spawners.add(new MobSpawnSettings.SpawnerData(type, weight, minCount, maxCount));
    }

    private static boolean isBiomeExcluded(BiomeLoadingEvent event, ResourceKey<Biome>[] excludedBiomes) {
        String eventBiomeName = event.getName().toString();
        return Arrays.stream(excludedBiomes)
                .map(ResourceKey::toString)
                .anyMatch(biomeName -> biomeName.equals(eventBiomeName));
    }

    private static boolean isBiomeIncluded(BiomeLoadingEvent event, ResourceKey<Biome>[] includedBiomes) {
        String eventBiomeName = event.getName().toString();
        return Arrays.stream(includedBiomes)
                .map(ResourceKey::toString)
                .anyMatch(biomeName -> biomeName.equals(eventBiomeName));
    }
}
