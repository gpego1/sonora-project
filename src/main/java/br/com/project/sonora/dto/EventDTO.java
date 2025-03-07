package br.com.project.sonora.dto;

import br.com.project.sonora.models.GeneralMusic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private Date date;
    private AddressDTO address;
    private List<GeneralMusic> genres;
    private List<UserDTO> artists;
    private List<UserDTO> hosts;
}
