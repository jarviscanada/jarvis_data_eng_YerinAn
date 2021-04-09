package ca.jrvs.practice.data_structure.stack_queue;

/**
 * This is a simplified version of java.util.Queue
 * @param <E> the type of elements held in this collection
 */
public interface JQueue<E> {

  /**
   * This is equivalent enqueue operation in Queue ADT
   * Inserts the specified element into the queue represented by this deque
   * (in other words, at the tail of this deque) if it is possible to do so
   * immediately without violating capacity restrictions, returning
   * {@code true} upon success and throwing an
   * {@code IllgalStateException} if not space is currently available.
   * @param e the element to add
   * @return {@code true} (as specified by {@link Collection#add})
   * @throws NullPointerException if the specified element is null and this
   * dequeue does not permit null elements
   */
  boolean add(E e);

  /**
   * This is equivalent dequeue operation in Queue ADT
   * Retrieves and removes the head of the queue represented by this dequeue
   * (in other words, the first element of this deque)
   * @return the head of the queue represented by this dequeue
   * @throws java.util.NoSuchElementException if this dequeue is empty
   */
  E remove();

  /**
   * Retrieves, but does not remove, the head of the queue represneted by
   * this dequeue (in other words, the first element of this deque), or
   * returns {@code null} if this dequeue is empty
   * @return the head of the queue represented by this deque, or
   * {@code null} if this deque is empty
   */
  E peekQ();

}
