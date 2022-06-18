package usuario.apijwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usuario.apijwt.model.Usuario;
import usuario.apijwt.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findByIdUsuario(@PathVariable(value = "id") Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return new ResponseEntity(usuario.get(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> findAll(){
        List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){

        for (int index = 0; index < usuario.getTelefones().size(); index ++) {
            usuario.getTelefones().get(index).setUsuario(usuario);
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Usuario> savePut(@RequestBody Usuario usuario){

        for (int index = 0; index < usuario.getTelefones().size(); index ++) {
            usuario.getTelefones().get(index).setUsuario(usuario);
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByIdUsuario(@PathVariable (value = "id") Long id) {
        usuarioRepository.deleteById(id);
        return new ResponseEntity<String>("id:" + id + " excluido", HttpStatus.OK);
    }

}

