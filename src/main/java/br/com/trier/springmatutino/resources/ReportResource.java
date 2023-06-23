package br.com.trier.springmatutino.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.domain.dto.CorridaPaisAnoDTO;
import br.com.trier.springmatutino.domain.dto.PistaCampeonatoIdDTO;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;

@RestController
@RequestMapping("/reports")
public class ReportResource {
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private PistaService pistaService;
	
	@Autowired
	private CorridaService corridaService;
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	@GetMapping ("/corrida-por-pais-ano/{paisId}/{ano}")
	public ResponseEntity<CorridaPaisAnoDTO>  findCorridaPorPaisEAno(@PathVariable Integer paisId, @PathVariable Integer ano){
		Pais pais = paisService.findById(paisId);
		List<Pista> pistasPorPais = pistaService.findByPaisOrderByTamanhoDesc(pais);		
		List<Corrida> corridas = new ArrayList<>();
		List<CorridaDTO> corridasDTO = new ArrayList<>();
		
		for(Pista pistas : pistasPorPais) {
			try {
				corridas.addAll(corridaService.findByPista(pistas));
			}catch (ObjetoNaoEncontrado e) {
				//FIXME verificar tratamento adequado
			}
			
		}
		
		for(Corrida corrida : corridas) {
			if(corrida.getData().getYear() == ano) {
				corridasDTO.add(corrida.toDTO());
			}
		}
		
		CorridaPaisAnoDTO corridaPaisAnoDTO = new CorridaPaisAnoDTO(ano, pais.getName(), corridasDTO);
		return ResponseEntity.ok(corridaPaisAnoDTO);
	}
	
	@GetMapping ("/campeonato-por-pista/{idCampeonato}")
	public ResponseEntity<PistaCampeonatoIdDTO>  findCorridaPorPaisEAno(@PathVariable Integer idCampeonato){
		Campeonato campeonato = campeonatoService.findById(idCampeonato);
		List<Corrida> corridas = corridaService.findByCampeonato(campeonato);		
		List<Pista> pistas = new ArrayList<>();
		
		for(Corrida corrida : corridas) {
			try {
				pistas.addAll(corrida.));
			}catch (ObjetoNaoEncontrado e) {
				//FIXME verificar tratamento adequado
			}			
		}
		
		PistaCampeonatoDTO pistaCampeonato = new PistaCampeonatoDTO(campeonato.getId(), campeonato.getDescricao(), pistas);
		return ResponseEntity.ok(pistaCampeonato);
	}
	
	
	
	/*
	Refatorado CHATGPT
	public ResponseEntity<CorridaPaisAnoDTO> findCorridaPorPaisEAno(@PathVariable Integer paisId, @PathVariable Integer ano) {
	    Pais pais = paisService.findById(paisId);
	    List<CorridaDTO> corridasDTO = new ArrayList<>();

	    for (Pista pista : pistaService.findByPaisOrderByTamanhoDesc(pais)) {
	        try {
	            List<Corrida> corridas = corridaService.findByPista(pista);
	            for (Corrida corrida : corridas) {
	                if (corrida.getData().getYear() == ano) {
	                    corridasDTO.add(corrida.toDTO());
	                }
	            }
	        } catch (ObjetoNaoEncontrado e) {
	            // FIXME verificar tratamento adequado
	        }
	    }

	    CorridaPaisAnoDTO corridaPaisAnoDTO = new CorridaPaisAnoDTO(ano, pais.getName(), corridasDTO);
	    return ResponseEntity.ok(corridaPaisAnoDTO);
	}
	*/
	
}