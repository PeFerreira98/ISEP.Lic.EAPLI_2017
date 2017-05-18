/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.ecafeteria.persistence;

import eapli.ecafeteria.domain.rating.Comment;
import eapli.framework.persistence.repositories.DataRepository;

/**
 *
 * @author Pedro Pereira
 */
public interface CommentRepository extends DataRepository<Comment, Long>{
    
    Iterable<Comment> findByText(String text);
    
}