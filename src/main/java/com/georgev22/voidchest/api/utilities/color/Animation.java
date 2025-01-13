package com.georgev22.voidchest.api.utilities.color;

import com.georgev22.voidchest.api.maps.ObjectMap;
import com.georgev22.voidchest.api.utilities.BukkitMinecraftUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Animation {

    private static final boolean bool = BukkitMinecraftUtils.MinecraftVersion.getCurrentVersion().isAboveOrEqual(BukkitMinecraftUtils.MinecraftVersion.V1_16_R1);

    private static final ObjectMap<String, List<String>> animationCache = ObjectMap.newHashObjectMap();

    public static String wave(String string, List<Color> colors) {
        return wave(string, true, 5, 10, colors);
    }

    public static String wave(String string, boolean bold, int start, int end, List<Color> colors) {
        Preconditions.checkArgument(colors.size() > 1, "Not enough colors provided");
        String str = "wave-" + string + "-" + bold + "-" + start + "-" + end + "-" + colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));
        if (animationCache.containsKey(str)) {
            return currentFrame(animationCache.get(str));
        } else {
            ArrayList<String> frames = Lists.newArrayList();
            int i = 0;

            for (Color color : colors) {
                Color color2 = colors.get(colors.size() == i + 1 ? 0 : i + 1);
                frames.addAll(Collections.nCopies(start, color.getAppliedTag(bool, MinecraftColor.getClosest(color).getAppliedTag()) + (bold ? "\u00A7l" : "") + string));
                ArrayList<String> list = Lists.newArrayList();
                list.addAll(Collections.nCopies(string.length(), color.getAppliedTag(bool, MinecraftColor.getClosest(color).getAppliedTag())));
                list.addAll(ColorCalculations.getColorsInBetween(color, color2, end).stream().map(c -> c.getAppliedTag(bool, MinecraftColor.getClosest(c).getAppliedTag())).toList());
                list.addAll(Collections.nCopies(string.length(), color2.getAppliedTag(bool, MinecraftColor.getClosest(color2).getAppliedTag())));

                for (int j = 0; j <= list.size() - string.length(); ++j) {
                    StringBuilder stringBuilder = new StringBuilder();
                    byte b2 = 0;
                    char[] chars = string.toCharArray();

                    for (char c : chars) {
                        String str1 = list.get(b2 + j);
                        stringBuilder.append(str1).append(bold ? "\u00A7l" : "").append(c);
                        ++b2;
                    }

                    frames.add(stringBuilder.toString());
                }

                frames.addAll(Collections.nCopies(start, color2.getAppliedTag(bool, MinecraftColor.getClosest(color2).getAppliedTag()) + (bold ? "\u00A7l" : "") + string));
                ++i;
            }

            animationCache.put(str, frames);
            return currentFrame(frames);
        }
    }

    public static String fading(String string, List<Color> colors) {
        return fading(string, true, 10, 20, colors);
    }

    public static String fading(String string, boolean bold, int start, int end, List<Color> colors) {
        Preconditions.checkArgument(colors.size() > 1, "Not enough colors provided");
        String str = "fading-" + string + "-" + bold + "-" + start + "-" + end + "-" + colors.stream().map(Color::getColorCode).collect(Collectors.joining("-"));
        if (animationCache.containsKey(str)) {
            return currentFrame(animationCache.get(str));
        } else {
            ArrayList<String> frames = Lists.newArrayList();
            int i = 0;

            for (Color color : colors) {
                Color color2 = colors.get(colors.size() == i + 1 ? 0 : i + 1);
                frames.addAll(Collections.nCopies(start, color.getAppliedTag(bool, MinecraftColor.getClosest(color).getAppliedTag()) + (bold ? "\u00A7l" : "") + string));

                for (Color color3 : ColorCalculations.getColorsInBetween(color, color2, end)) {
                    frames.add(color3.getAppliedTag(bool, MinecraftColor.getClosest(color).getAppliedTag()) + (bold ? "\u00A7l" : "") + string);
                }

                ++i;
            }

            animationCache.put(str, frames);
            return currentFrame(frames);
        }
    }

    private static String currentFrame(List<String> frames) {
        long l = System.currentTimeMillis() / 50L;
        int i = (int) (l % (long) frames.size());
        return frames.get(i);
    }
}
