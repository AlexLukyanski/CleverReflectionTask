package by.clever.reflection.service.impl;

import by.clever.reflection.dao.MusicBandDAO;
import by.clever.reflection.dao.proxy.MusicBandDAOProxy;
import by.clever.reflection.dto.MusicBandDTO;
import by.clever.reflection.entity.MusicBand;
import by.clever.reflection.mapper.MusicBandMapper;
import by.clever.reflection.mapper.MusicBandMapperImpl;
import by.clever.reflection.service.MusicBandService;
import by.clever.reflection.service.exception.ServiceValidationException;
import by.clever.reflection.service.validation.MusicBandDTOValidation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of MusicBandService
 */
@RequiredArgsConstructor
public class MusicBandServiceImpl implements MusicBandService {

    private final MusicBandMapper mapper;
    private final MusicBandDAO musicBandDAO;

    public MusicBandServiceImpl() {
        this.mapper = new MusicBandMapperImpl();
        this.musicBandDAO = MusicBandDAOProxy.getInstance();
    }

    /**
     * @param bandDTO Object that need to be saved
     * @return UUID of saved object
     */
    @Override
    public UUID create(MusicBandDTO bandDTO) {

        boolean isBandDTOValid = validate(bandDTO);

        if (!isBandDTOValid) {
            throw new ServiceValidationException("MusicBandDTO is not valid");
        }

        MusicBand band = mapper.toMusicBand(bandDTO);
        UUID id = musicBandDAO.save(band);
        return id;
    }

    /**
     * @param id Id of object to be retrieved
     * @return DTO representation of retrieved MusicBand object
     */
    @Override
    public MusicBandDTO getByID(UUID id) {

        if (id == null) {
            throw new ServiceValidationException("ID must not be null");
        }

        Optional<MusicBand> band = musicBandDAO.readByID(id);
        MusicBandDTO bandDTO = mapper.toMusicBandDTO(band.orElseThrow());
        return bandDTO;
    }

    /**
     * @param bandDTO Object that need to be updated
     * @return UUID of updated object
     */
    @Override
    public UUID update(MusicBandDTO bandDTO) {

        boolean isBandDTOValid = validate(bandDTO);

        if (!isBandDTOValid) {
            throw new ServiceValidationException("MusicBandDTO is not valid");
        }

        MusicBand band = mapper.toMusicBand(bandDTO);
        UUID id = musicBandDAO.update(band);
        return id;
    }

    /**
     * @param id Id of the object that need to be deleted
     */
    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new ServiceValidationException("ID must not be null");
        }

        musicBandDAO.delete(id);
    }

    private boolean validate(MusicBandDTO bandDTO) {
        MusicBandDTOValidation musicBandDTOValidation = MusicBandDTOValidation.builder()
                .isIdValid(bandDTO.id())
                .isNameValid(bandDTO.name())
                .isGenreValid(bandDTO.genre())
                .isCreationDateValid(bandDTO.creationDate())
                .isWorkPhoneNumberValid(bandDTO.workPhoneNumber())
                .isWorkEmailValid(bandDTO.workEmail())
                .build();

        return musicBandDTOValidation.isMusicBandDTOValid();
    }
}
