package by.clever.reflection.mapper;

import by.clever.reflection.dto.MusicBandDTO;
import by.clever.reflection.entity.MusicBand;
import org.mapstruct.Mapper;

@Mapper
public interface MusicBandMapper {

    MusicBand toMusicBand(MusicBandDTO bandDTO);

    MusicBandDTO toMusicBandDTO(MusicBand band);
}
